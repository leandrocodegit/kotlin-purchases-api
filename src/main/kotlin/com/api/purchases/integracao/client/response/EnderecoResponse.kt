package com.api.purchases.integracao.client.response

class EnderecoResponse(
        var id: Long,
        var rua: String,
        var numero: String,
        var cidade: String,
        var estado: String,
        var isDelivery: Boolean){

        override fun toString(): String {
                return "${rua} ${numero}, ${cidade} - ${estado}"
        }
}