package com.example.bigquery.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class BigqueryConfig {

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("classpath:gcp/verdant-bus-442213-d3-6130959fadde.json")
    Resource resource;

    @Bean
    public BigQuery bigQuery() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(resource.getFile()));
        return BigQueryOptions.newBuilder()
                .setCredentials(credentials)
                .setProjectId(projectId)
                .build()
                .getService();
    }

}
