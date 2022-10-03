package com.test.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.Book;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Singleton
public class DirectBookClient {
    private final HttpClient client = HttpClient.newHttpClient();
    private final String bookUrl;
    private final ObjectMapper mapper;

    @Inject
    public DirectBookClient(@Value("${bookUrl}") String bookUrl, ObjectMapper mapper) {
        this.bookUrl = bookUrl;
        this.mapper = mapper;
    }

    public Book getBook() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(bookUrl))
                .GET()
                .build();

        HttpResponse<InputStream> resp;
        try {
            resp = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            if (resp.statusCode() == 200) {
                try {
//                    String body = new BufferedReader(new InputStreamReader(resp.body(), StandardCharsets.UTF_8))
//                            .lines()
//                            .collect(Collectors.joining("\n"));
//                    System.out.println("BODY - [ " + body + " ]");
                    return mapper.readValue(resp.body(), Book.class);
                } catch (IOException e) {
                    throw new RuntimeException("ERROR PARSING BOOK");
                }
            }
            else {
                throw new RuntimeException("ERROR READING BOOK- status: " + resp.statusCode() + " url: " + bookUrl);
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException("There was a connection error while getting a book. URL: " + bookUrl, e);
        }

    }
}
