package com.firman.demo.crud.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.concurrent.atomic.AtomicBoolean;

public class PostgreTestContainerExtension implements BeforeAllCallback {

    private static AtomicBoolean started = new AtomicBoolean(false);

    public static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12.0").withReuse(true);

    @Override
    public void beforeAll(ExtensionContext extensionContext) {

        if (!started.get()) {
            postgreDBContainer.withDatabaseName("crud");
            postgreDBContainer.start();

            System.setProperty("spring.datasource.url", postgreDBContainer.getJdbcUrl());
            System.setProperty("spring.datasource.username", postgreDBContainer.getUsername());
            System.setProperty("spring.datasource.password", postgreDBContainer.getPassword());

            started.set(true);
        }
    }
}
