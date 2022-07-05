package com.api.purchases.integracao.product.rest

import com.api.purchases.exceptions.ResponseErro
import com.api.purchases.integracao.product.response.ProductResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "productReponse",
    url = "http://localhost:9090/api/v1/products")
interface RestProduct {

    @GetMapping("/{id}")
    fun requestProdust(@PathVariable id: Long): ProductResponse
}