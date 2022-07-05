package com.api.purchases.integracao.client.rest

import com.api.purchases.integracao.client.response.ClientResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "restClient",
    url = "http://localhost:9092/api/v1/users"
)
interface RestClient {

    @GetMapping("/{id}")
    fun requestClient(@PathVariable id: Long): ClientResponse
}