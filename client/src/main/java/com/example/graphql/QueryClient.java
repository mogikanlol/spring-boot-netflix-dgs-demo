package com.example.graphql;

import com.example.test.client.BooksGraphQLQuery;
import com.example.test.client.BooksProjectionRoot;
import com.example.test.types.Book;
import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class QueryClient {

    public List<Book> getBooks() {
        WebClient webClient = WebClient.create("http://localhost:8095/graphql");
        WebClientGraphQLClient client = MonoGraphQLClient.createWithWebClient(webClient);

        Coercing coercing = ExtendedScalars.GraphQLBigDecimal.getCoercing();
        GraphQLQueryRequest request = new GraphQLQueryRequest(
                BooksGraphQLQuery.newRequest().build(),
                new BooksProjectionRoot().id().profit().releaseDate().title(),
                Map.of(BigDecimal.class, (Coercing<?, ?>) coercing)
        );
        GraphQLResponse response = client
                .reactiveExecuteQuery(request.serialize())
                .block();

        return response.extractValueAsObject("data.books", new TypeRef<List<Book>>() {});
    }
}
