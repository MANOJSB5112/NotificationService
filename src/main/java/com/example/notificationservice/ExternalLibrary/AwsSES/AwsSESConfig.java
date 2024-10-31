package com.example.notificationservice.ExternalLibrary.AwsSES;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class AwsSESConfig {
//    @Value("${cloud.aws.ses.access-key}")
//    private String awsAccessKey;
//
//    @Value("${cloud.aws.ses.secret-key}")
//    private String awsSecretKey;
//
//    @Value("${cloud.aws.ses.region.static}")
//    private String awsRegion;
//
//    AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
//            new BasicAWSCredentials(awsAccessKey, awsSecretKey)
//    );
    @Bean
    public SesClient sesEmailService() {
        return SesClient.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
