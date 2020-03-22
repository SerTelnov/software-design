package com.telnov.software_design.reactive_app.config;

import javax.annotation.Nonnull;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class MongoReactiveApplication extends AbstractReactiveMongoConfiguration {

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Nonnull
    @Override
    protected String getDatabaseName() {
        return "reactive";
    }

    @Nonnull
    @Override
    public MongoClient reactiveMongoClient() {
        return mongoClient();
    }
}
