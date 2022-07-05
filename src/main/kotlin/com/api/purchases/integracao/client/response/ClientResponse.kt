package com.api.purchases.integracao.client.response

class ClientResponse(
    var id: Long,
    var email: String,
    var nome: String,
    var enderecos: List<EnderecoResponse>,
    var idade: Int,
    var telefone: String,
    var isActive: Boolean) {
}