package com.api.purchases.model

import javax.validation.constraints.Min

class Product(
    var idProduct: Long,
    var name: String?,
    var qtd: Int,
    var valor: Double)