package com.shadyplace.springweb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;


@Configuration
public class PaypalConfig {
    @Bean
    public PayPalHttpClient getPaypalClient(
            @Value("${paypal.clientId}") String clientId,
            @Value("${paypal.clientSecret}") String clientSecret) {

        return new PayPalHttpClient(new PayPalEnvironment.Sandbox(clientId, clientSecret));
    }
}
