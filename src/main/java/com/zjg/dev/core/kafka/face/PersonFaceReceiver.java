package com.zjg.dev.core.kafka.face;

import com.zjg.dev.core.kafka.AbstractKafkaReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * 接收用户人脸数据
 */
@Slf4j
public class PersonFaceReceiver implements AbstractKafkaReceiver {

    @Override
    @KafkaListener(topics = {"${kafka.face.topic}"}, containerFactory = "kafkaFaceListenerContainerFactory")
    public void receiveMessage(String message) {
        log.info("PersonFaceReceiver=>>" + message);
    }
}
