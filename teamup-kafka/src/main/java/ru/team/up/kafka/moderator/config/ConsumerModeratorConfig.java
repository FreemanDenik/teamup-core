package ru.team.up.kafka.moderator.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация consumer kafka для модераторов
 */

@Configuration
public class ConsumerModeratorConfig {
    /**
     * Значение groupId, которе определяет группу консьюмеров, в рамках которой доставляется один экземпляр сообщения.
     * Например, при трех консьюмеров в одной группе, слушающих один Topic сообщение достанется, только, одному
     */
    @Value(value = "${kafka.group.id}")
    private String kafkaGroupId;
    /**
     * Адрес bootstrap сервера kafka
     */
    @Value(value = "${kafka.server.address}")
    private String kafkaServer;

    /**
     * Метод конфигурации consumer
     *
     * @return возвращает мапу с настройками consumer
     */
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return props;
    }

    /**
     * Метод создает фабрику consumer
     *
     * @return возвращает объект org.springframework.kafka.core.DefaultKafkaConsumerFactory
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    /**
     * @return возвращает объект org.springframework.kafka.core.KafkaListenerContainerFactory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
