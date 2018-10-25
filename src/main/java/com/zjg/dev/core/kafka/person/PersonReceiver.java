package com.zjg.dev.core.kafka.person;

import com.zjg.dev.core.kafka.AbstractKafkaReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * 接收住户数据修改
 */
@Slf4j
public class PersonReceiver implements AbstractKafkaReceiver {
    @Override
    @KafkaListener(topics = {"${kafka.person.topic}"}, containerFactory = "kafkaOwnListenerContainerFactory")
    public void receiveMessage(String message) {
        log.info("PersonReceiver==>>>" + message);
    }

}
