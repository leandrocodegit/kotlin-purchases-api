package com.api.purchases.controller.request

import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.types.ObjectId
import reactor.util.annotation.NonNull
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PurchasesRequest(
    @field:Min(1)
    var idClient: Long,
    @field:Valid
    var product: ProductRequest)
