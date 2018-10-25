package com.zjg.dev.core.kafka.face;


import com.zjg.dev.core.kafka.AbstractKafkaConfig;
import com.zjg.dev.core.kafka.AbstractKafkaReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

/**
 * 接收用户人脸数据
 */
@Configuration
@EnableKafka
@Slf4j
public class PersonFaceConfig extends AbstractKafkaConfig {
    @Value("${kafka.person.bootstrap.servers}")
    private String servers;

    @Value("${kafka.person.group.id}")
    private String groupId;

    @Value("${kafka.enable.auto.commit}")
    private boolean enableAutoCommit;

    @Value("${kafka.auto.commit.interval.ms}")
    private String autoCommitInterval;

    @Value("${kafka.session.timeout.ms}")
    private String sessionTimeout;

    @Value("${kafka.sasl.mechanism}")
    private String mechanism;

    @Value("${kafka.person.security.protocol}")
    private String protocol;


    @Override
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaOwnListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<String, String>(
                consumerFactoryConfig(servers, groupId, enableAutoCommit, autoCommitInterval, sessionTimeout, mechanism, protocol)));
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }


    @Override
    public Map<String, Object> consumerFactoryConfig(String servers, String groupId, boolean enableAutoCommit, String autoCommitInterval, String sessionTimeout, String mechanism, String protocol) {
        return super.consumerFactoryConfig(servers, groupId, enableAutoCommit, autoCommitInterval, sessionTimeout, mechanism, protocol);
    }

    @Bean
    public AbstractKafkaReceiver receiver() {
        return new PersonFaceReceiver();
    }
}
