package com.example.notificationservice.AwsSQS;


import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
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
public class AwsSQSConfig {

    @Value("${sqs.access-key}")
    private  String SQS_ACCESS_KEY;

    @Value("${sqs.secret-key}")
    private String SQS_ACCESS_SECRET_KEY;

    @Value("${sqs.region.static}")
    private String SQS_REGION;

    private final ObjectMapper objectMapper;

    @Autowired
    public AwsSQSConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(SQS_ACCESS_KEY, SQS_ACCESS_SECRET_KEY)
        );
    }


    @Bean
    public QueueMessagingTemplate queueMessagingTemplate() {
        return new QueueMessagingTemplate(amazonSQSAsync());
    }

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsync() {
        return AmazonSQSAsyncClientBuilder.standard()
                .withRegion(SQS_REGION)
                .withCredentials(awsCredentialsProvider())
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
        messageConverter.setStrictContentTypeMatch(false);

        List<MessageConverter> messageConverterList = new ArrayList<>();
        messageConverterList.add(messageConverter);
        messageConverterList.add(new NotificationRequestConverter(messageConverter));
        messageConverterList.add(new SimpleMessageConverter());

        CompositeMessageConverter compositeMessageConverter = new CompositeMessageConverter(messageConverterList);
        queueMessageHandlerFactory.setArgumentResolvers(Collections.singletonList(new NotificationMessageArgumentResolver(compositeMessageConverter)));

        return queueMessageHandlerFactory.createQueueMessageHandler();
    }

    @Bean
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



