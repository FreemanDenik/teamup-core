package ru.teamup.teamupsup.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.team.up.dto.SupParameterDto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stepan Glushchenko
 * Consumer kafka для получения параметров системы
 */


@EnableKafka
@Configuration
public class KafkaConsumerSupConfig {
    /**
     * Адрес bootstrap сервера kafka
     */
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    /**
     * Id группы консьюмеров
     */
    @Value(value = "${kafka.group.id}")
    private String groupId;

    /**
     * Конфигурация фабрики производителей
     *
     * @return возвращает объект класса org.springframework.kafka.core.DefaultKafkaProducerFactory
     */
    public ConsumerFactory<? super String, ? super SupParameterDto<?>> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("INPUT_KEY", SupParameterDto.class);
        typeMapper.setIdClassMapping(classMap);
        typeMapper.addTrustedPackages("*");

        JsonDeserializer<SupParameterDto<?>> jsonDeserializer = new JsonDeserializer<>(SupParameterDto.class);
        jsonDeserializer.setTypeMapper(typeMapper);
        jsonDeserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), jsonDeserializer);
    }

    /**
     * @return возвращает объект org.springframework.kafka.core.KafkaTemplate
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SupParameterDto<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SupParameterDto<?>> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}