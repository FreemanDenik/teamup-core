package ru.team.up.kafka.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.team.up.core.entity.Account;
import ru.team.up.dto.KafkaEventDto;
import ru.team.up.moderator.payload.AssignedEventPayload;
import ru.team.up.payload.Payload;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация consumer kafka для модераторов
 */

@Configuration
@EnableKafka
@PropertySource("classpath:kafka.properties")
public class ConsumerModeratorConfig {

    /**
     * Значение groupId, которе определяет группу консьюмеров, в рамках которой доставляется один экземпляр сообщения.
     * Например, при трех консьюмеров в одной группе, слушающих один Topic сообщение достанется, только, одному
     */
    @Value(value = "${kafka.group.id}")
    private String kafkaGroupId;
    /**
     * Адрес сервера kafka
     */
    @Value(value = "${kafka.server.address}")
    private String kafkaServer;

    @Bean
    public ConsumerFactory<String, KafkaEventDto> consumerFactory() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Игнор свойств суперкласса(в данном случае игнор свойств Account, которые есть у Moderator)
        mapper.setAnnotationIntrospector(new IgnoreInheritedIntrospector());
        SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());

        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(Payload.class, AssignedEventPayload.class);

        module.setAbstractTypes(resolver);

        mapper.registerModule(module);

        JsonDeserializer<KafkaEventDto> deserializer = new JsonDeserializer<>(mapper);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(false);

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, mapper);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);


        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, KafkaEventDto> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaEventDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // Игнор свойств суперкласса(в данном случае игнор свойств Account, которые есть у Moderator)
    public static class IgnoreInheritedIntrospector extends JacksonAnnotationIntrospector {
        @Override
        public boolean hasIgnoreMarker(final AnnotatedMember m) {
            return m.getDeclaringClass() == Account.class || super.hasIgnoreMarker(m);
        }
    }
}
