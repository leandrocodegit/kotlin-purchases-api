package com.api.purchases.build

import com.api.purchases.controller.request.ProductRequest
import com.api.purchases.controller.request.PurchasesRequest
import com.api.purchases.integracao.client.response.ClientResponse
import com.api.purchases.integracao.client.response.EnderecoResponse
import com.api.purchases.integracao.product.response.ProductResponse
import com.api.purchases.model.Product
import com.api.purchases.model.Purchases
import org.bson.types.ObjectId

class Builds(

    val id: ObjectId = ObjectId.get(),
    var idClient: Long = 45,
    var product: Product = Product(1,"Produto test",2,100.0),
    var total: Double = 200.0) {

    fun createPurchases(): Purchases{
        return Purchases(
            id,
            idClient,
            product,
            total,
            "Endereco de entrega")
    }
    fun createPurchasesRequest(): PurchasesRequest{
        return PurchasesRequest(
            idClient,
            ProductRequest(
                1,
                2))
    }

    fun creatProducResponse(): ProductResponse{
        return ProductResponse(
            product.idProduct,
            "Produto teste",
            100,
            100.0,
            "Descricao teste",
            true)
    }

    fun createClientResponse(): ClientResponse{
        return ClientResponse(
            1L,
            "email@tesst.com",
            "Fulano",
            listOf(EnderecoResponse(1,
                "rua",
                "0",
                "cidade",
                "estado",
                true)),
        18,
        "11111111",
        true)
    }
}