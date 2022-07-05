package com.api.purchases.repository

import com.api.purchases.build.Builds
import com.api.purchases.model.Purchases
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class PurchasesRepositoryTest(){

    @MockkBean
    private lateinit var comprasRepository: PurchasesRepository

    private lateinit var purchases: Purchases
    private lateinit var id: ObjectId

    @Test
    fun `test save new purchases`(){

        purchases = Builds().createPurchases()
        every { comprasRepository.save(purchases) } returns purchases
        comprasRepository.save(purchases)
    }

    @Test
    fun `test find purchases by id`(){

        purchases = Builds().createPurchases()
        id = purchases.id
        every { comprasRepository.findById(id) } returns Optional.of(purchases)
        assert(comprasRepository.findById(id).isPresent)
    }
}