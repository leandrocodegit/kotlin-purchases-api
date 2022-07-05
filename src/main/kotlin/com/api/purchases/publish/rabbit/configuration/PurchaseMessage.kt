package com.api.rabbitmq.message

class PurchasesResponseMQ(
    var idClient: Long)

class ProductResponse(
    var idProduct: Long,
    var name: String,
    var qtd: Int,
    var valor: Double)