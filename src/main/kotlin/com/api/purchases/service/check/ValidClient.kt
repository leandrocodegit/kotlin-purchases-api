package com.api.purchases.service.check

import com.api.purchases.enuns.CodeError
import com.api.purchases.exceptions.EntityException
import com.api.purchases.integracao.client.response.ClientResponse
import com.api.purchases.integracao.client.response.EnderecoResponse
import com.api.purchases.model.Purchases

class ValidClient(var clientResponse: ClientResponse, purchases: Purchases): ValidPurchases(purchases) {

    override fun check() {
        if(clientResponse.idade < 18)
            throw EntityException("Compra nao permitida a menores de 18 anos", CodeError.AGE_ACCEPT)
        if(clientResponse.enderecos.isEmpty())
            throw EntityException("Cliente nao possui um endereco valido", CodeError.ADDRESS_NOT_FOUND)
        var isContainsAddressDelivery: Boolean = clientResponse.enderecos.map { e -> e.isDelivery}.contains(true)
        if(isContainsAddressDelivery.not()) throw EntityException("Cliente nao possui um endereco de entrega", CodeError.ADDRESS_NOT_FOUND)
    }
}