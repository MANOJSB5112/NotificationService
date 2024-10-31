package com.example.notificationservice.AwsSQS;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.cloud.aws.messaging.support.NotificationMessageArgumentResolver;
import org.springframework.cloud.aws.messaging.support.converter.NotificationRequestConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AwsSQSConfig {
//    @Value("${cloud.aws.sqs.access-key}")
    private String awsAccessKey="AKIA6ODU4XODLA3N5VPC";

//    @Value("${cloud.aws.sqs.secret-key}")
    private String awsSecretKey="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";

    @Value("${cloud.aws.sqs.region.static}")
    private String awsRegion;

    private final ObjectMapper objectMapper;

    AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
            new BasicAWSCredentials(awsAccessKey, awsSecretKey)
    );
////
////
////    @Bean
////    public QueueMessagingTemplate queueMessagingTemplate() {
////        return new QueueMessagingTemplate(amazonSQSAsync());
////    }
////
   @Bean
   @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(awsRegion)
                .withCredentials(awsCredentialsProvider)
                .build();
    }
    @Bean
    @Primary
    public QueueMessageHandler queueMessageHandler() {
        QueueMessageHandlerFactory queueMessageHandlerFactory = new QueueMessageHandlerFactory();
        queueMessageHandlerFactory.setAmazonSqs(amazonSQSAsync());

        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setSerializedPayloadClass(String.class);
        messageConverter.setObjectMapper(objectMapper);

        /*
         * https://cloud.spring.io/spring-cloud-aws/spring-cloud-aws.html
         * Because AWS messages does not contain the mime-type header, the Jackson message converter has to be configured
         * with the strictContentTypeMatch property false to also parse message without the proper mime type.
         */
        messageConverter.setStrictContentTypeMatch(false);

        List<MessageConverter> messageConverterList = new ArrayList<>();
        messageConverterList.add(messageConverter);
        messageConverterList.add(new NotificationRequestConverter(messageConverter));
        messageConverterList.add(new SimpleMessageConverter());

        CompositeMessageConverter compositeMessageConverter = new CompositeMessageConverter(messageConverterList);

        queueMessageHandlerFactory.setArgumentResolvers(Collections.singletonList(new NotificationMessageArgumentResolver(compositeMessageConverter)));
        QueueMessageHandler queueMessageHandler = queueMessageHandlerFactory.createQueueMessageHandler();
        return queueMessageHandler;
    }


    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.initialize();
        return executor;
    }

    @Bean
    @Primary
    public SimpleMessageListenerContainer simpleMessageListenerContainer(QueueMessageHandler queueMessageHandler) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setAmazonSqs(amazonSQSAsync());
        simpleMessageListenerContainer.setMessageHandler(queueMessageHandler);
        simpleMessageListenerContainer.setMaxNumberOfMessages(10);
        simpleMessageListenerContainer.setTaskExecutor(threadPoolTaskExecutor());
        return simpleMessageListenerContainer;
    }
}



