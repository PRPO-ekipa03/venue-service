package si.uni.prpo.group03.venueservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange paymentExchange() {
        return new TopicExchange("paymentExchange");
    }

    @Bean
    public Queue paymentConfirmedQueue() {
        return new Queue("paymentConfirmedQueue");
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder.bind(paymentConfirmedQueue()).to(paymentExchange()).with("payment.confirmed");
    }
}
