package com.api.purchases.repository

import com.api.purchases.model.Purchases
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository


interface PurchasesRepository: MongoRepository<Purchases, ObjectId>{
    fun findAllPurschasesByIdClient(id: Long): List<Purchases>
}