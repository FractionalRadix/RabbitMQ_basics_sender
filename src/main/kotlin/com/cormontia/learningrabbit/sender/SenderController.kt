package com.cormontia.learningrabbit.sender

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SenderController {

    init {
        val exchange = exchange()
        val queue = queue()
        val binding = binding(queue, exchange)
    }

    @Bean
    final fun exchange(): TopicExchange {
        return TopicExchange("test.rabbit.topic1")
    }

    @Bean
    final fun queue(): Queue {
        return Queue("test.rabbit.topic1", false)
    }

    @Bean
    final fun binding(queue: Queue, topicExchange: TopicExchange): Binding {
        return BindingBuilder.bind(queue).to(topicExchange).with("test.rabbit.*")
    }

    @Autowired
    private lateinit var rabbitTemplate: RabbitTemplate

    @GetMapping("/send")
    fun send(): String {
        rabbitTemplate.convertAndSend("test.rabbit.topic1", "test.rabbit.topic1", "This is the message!")
        return "Stub for the /send page."
    }
}
