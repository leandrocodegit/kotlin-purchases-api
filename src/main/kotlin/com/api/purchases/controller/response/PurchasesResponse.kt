package com.api.purchases.controller.response

class PurchasesResponse(
    var id: String,
    var idClient: Long,
    var product: ProductResponse,
    var total: Double,
    var addressDelivery: String)