package com.example;

import com.example.graphql.MutationClient;
import com.example.graphql.QueryClient;
import com.example.test.types.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        MutationClient mutationClient = ctx.getBean(MutationClient.class);
        QueryClient queryClient = ctx.getBean(QueryClient.class);

        List<Book> books = queryClient.getBooks();
        System.out.println(books);

        mutationClient.execute();

        ctx.close();
    }
}
