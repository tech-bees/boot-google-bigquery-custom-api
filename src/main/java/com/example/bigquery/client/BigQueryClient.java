package com.example.bigquery.client;

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BigQueryClient {

    @Autowired
    private BigQuery bigQuery;


    public TableResult query(String query){
        QueryJobConfiguration queryJobConfig = QueryJobConfiguration.newBuilder(query).build();
        TableResult result = null;
        try{
            result = bigQuery.query(queryJobConfig);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
