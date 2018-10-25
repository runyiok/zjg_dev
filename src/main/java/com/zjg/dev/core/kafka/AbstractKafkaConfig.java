package com.zjg.dev.core.kafka;

import com.google.common.collect.Maps;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;


public abstract class AbstractKafkaConfig {
    static {
        //Java安全之认证与授权
        System.setProperty("java.security.auth.login.config", AbstractKafkaConfig.class.getResource("/") + "kafka_client_jaas.conf");
    }

    public abstract KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaOwnListenerContainerFactory();

    public abstract AbstractKafkaReceiver receiver();

    /**
     * 加载配置
     *
     * @param servers
     * @param groupId
     * @param enableAutoCommit
     * @param autoCommitInterval
     * @param sessionTimeout
     * @param mechanism
     * @param protocol
     * @return
     */
    public Map<String, Object> consumerFactoryConfig(String servers, String groupId, boolean enableAutoCommit, String autoCommitInterval, String sessionTimeout, String mechanism, String protocol) {
        Map<String, Object> configs = Maps.newHashMap();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        configs.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configs.put("security.protocol", protocol);
        configs.put("sasl.mechanism", mechanism);
        return configs;
    }
}
