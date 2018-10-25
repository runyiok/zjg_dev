package com.zjg.dev.core.kafka;

public interface AbstractKafkaReceiver {
    //接收消息
    public void receiveMessage(String message);

}
