package com.api.purchases

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CompraApiApplication

fun main(args: Array<String>) {
	runApplication<CompraApiApplication>(*args)
}
