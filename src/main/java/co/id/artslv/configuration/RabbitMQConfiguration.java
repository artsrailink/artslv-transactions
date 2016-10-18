package co.id.artslv.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gemuruhgeo on 08/09/16.
 */
@Configuration
public class RabbitMQConfiguration {
    public static final String queueName = "transaction-queue";
    public static final String exchangeName = "exchange-name";

    @Bean
    Queue queue(){
        return new Queue(queueName,true,false,false);
    }
    @Bean
    TopicExchange exchange(){
        return new TopicExchange(exchangeName);
    }
    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    @Bean
    CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost("172.16.8.35");
        cachingConnectionFactory.setPort(5672);
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }
    @Bean(name = "rabbitTemplateMod")
    RabbitTemplate rabbitTemplate(CachingConnectionFactory cachingConnectionFactory, MessageConverter ms){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setMessageConverter(ms);

        rabbitTemplate.setConnectionFactory(cachingConnectionFactory);
        return rabbitTemplate;
    }
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
    }


}
