package com.test;

import com.test.clients.DirectBookClient;
import com.test.clients.MicronautBookClient;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;

@Controller("/hello")
public class HelloController {
    private final DirectBookClient directBookClient;
    private final MicronautBookClient mnBookClient;

    @Inject
    public HelloController(DirectBookClient directBookClient, MicronautBookClient mnBookClient) {
        this.directBookClient = directBookClient;
        this.mnBookClient = mnBookClient;
    }

    @Get("/direct")
    public Book direct() {
        return directBookClient.getBook();
    }

    @Get("/micronaut")
    public Book micronaut() {
        return mnBookClient.getBook();
    }
}
