package com.api.purchases.publish.rabbit.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.*
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitConfig {

    @Bean
    fun purchaseBinging():Binding{
        return BindingBuilder
            .bind(purchasesModelQueue())
            .to( directExchange())
            .with("")
            .noargs()
    }

    @Bean
    fun directExchange() : Exchange{
        return ExchangeBuilder
            .directExchange("DIRECT-EXCHANGE")
            .durable(true)
            .build()
    }

    @Bean
    fun purchasesModelQueue() : Queue {
        return QueueBuilder
            .durable("PURCHASES-MODEL-QUEUE")
            .build()
    }

    @Bean
    fun messageConverter(objectMapper: ObjectMapper) : MessageConverter{
        return Jackson2JsonMessageConverter(objectMapper)
    }
}