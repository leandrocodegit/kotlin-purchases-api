package com.api.purchases.mapper

import com.api.purchases.controller.request.PurchasesRequest
import com.api.purchases.controller.response.PurchasesResponse
import com.api.purchases.model.Purchases
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface PurchasesMapper {

    @Mappings(
        Mapping(target = "id", expression = "java(ObjectId.get())"),
        Mapping(target = "product.name", ignore = true) ,
        Mapping(target = "product.valor", ignore = true),
        Mapping(target = "total", ignore = true),
        Mapping(target = "addressDelivery", ignore = true)
    )
    fun toEntity(purchasesRequest: PurchasesRequest): Purchases
    @Mappings(
        Mapping(target = "id", expression = "java(purchases.getId().toString())")
    )
    fun toResponse(purchases: Purchases): PurchasesResponse
}