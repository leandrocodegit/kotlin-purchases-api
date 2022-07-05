package com.api.purchases.service

import com.api.purchases.enuns.CodeError
import com.api.purchases.exceptions.EntityException
import com.api.purchases.integracao.client.response.ClientResponse
import com.api.purchases.integracao.client.response.EnderecoResponse
import com.api.purchases.integracao.client.rest.RestClient
import com.api.purchases.integracao.product.response.ProductResponse
import com.api.purchases.integracao.product.rest.RestProduct
import com.api.purchases.mapper.PurchasesMapper
import com.api.purchases.model.Product
import com.api.purchases.model.Purchases
import com.api.purchases.repository.PurchasesRepository
import com.api.purchases.service.check.CheckPurchases
import com.api.purchases.service.check.ValidClient
import com.api.purchases.service.check.ValidProduct
import com.api.purchases.service.check.ValidPurchases
import org.bson.types.ObjectId
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class PurchasesService(
    private var purchasesRepository: PurchasesRepository,
    private val restProduct: RestProduct,
    private val restClient: RestClient,
    private val rabbitTemplate: RabbitTemplate,
    private val purchasesMapper: PurchasesMapper
) {

    fun savePurchases(purchases: Purchases): Purchases {
        val productResponse: ProductResponse = restProduct.requestProdust(purchases.product.idProduct)
        CheckPurchases(ValidProduct(productResponse, purchases)).validPurchases.check()

        val clientResponse: ClientResponse = restClient.requestClient(purchases.idClient)
        CheckPurchases(ValidClient(clientResponse, purchases)).validPurchases.check()

        var listEndereco: List<EnderecoResponse> = clientResponse.enderecos.filter { e -> e.isDelivery}

        var product: Product = Product(
            productResponse.id,
            productResponse.nome,
            purchases.product.qtd,
            productResponse.valor
        )

        purchases.addressDelivery = listEndereco.first().toString()
        purchases.total = product.valor * purchases.product.qtd
        purchases.product = product
        rabbitTemplate.convertAndSend("DIRECT-EXCHANGE","", purchasesMapper.toResponse(purchases))
        return purchasesRepository.save(purchases)
    }

    fun listAllPurchasesClient(id: Long): List<Purchases> {
        return purchasesRepository.findAllPurschasesByIdClient(id)
    }

    fun buscaPurchases(id: ObjectId): Purchases {
        var optCompras: Optional<Purchases> = purchasesRepository.findById(id)
        return optCompras.orElseThrow { throw EntityException("Id de compra nao encontrado", CodeError.NOT_FOUND) }
    }

}