package com.api.purchases.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Purchases(
    @Id
    val id: ObjectId = ObjectId.get(),
    var idClient: Long,
    var product: Product,
    var total: Double,
    var addressDelivery: String?)