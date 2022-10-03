package com.test.clients;

import com.test.Book;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("${bookUrl}")
public interface MicronautBookClient {
    @Get(consumes = MediaType.APPLICATION_JSON)
    String getBook();
}
