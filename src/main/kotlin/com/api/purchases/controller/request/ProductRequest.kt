package com.api.purchases.controller.request

import javax.validation.constraints.Min

class ProductRequest(
    @field:Min(1)
    var idProduct: Long,
    @field:Min(1)
    var qtd: Int)