package com.example.graphql;

import com.example.test.client.TestGraphQLQuery;
import com.example.test.types.Game;
import com.netflix.graphql.dgs.client.MonoGraphQLClient;
import com.netflix.graphql.dgs.client.WebClientGraphQLClient;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MutationClient {

    public void execute() {
        WebClient webClient = WebClient.create("http://localhost:8095/graphql");
        WebClientGraphQLClient client = MonoGraphQLClient.createWithWebClient(webClient);

        List<Game> games = new ArrayList<>();
        games.add(
                Game.newBuilder()
                        .id(1L)
                        .title("Mario")
                        .releaseDate(LocalDate.now())
                        .profit(BigDecimal.valueOf(1.32413512))
                        .build()
        );
        Coercing coercing = ExtendedScalars.GraphQLBigDecimal.getCoercing();
        GraphQLQueryRequest www = new GraphQLQueryRequest(
                TestGraphQLQuery.newRequest().games(games).build(),
                null,
                Map.of(BigDecimal.class, (Coercing<?, ?>) coercing)
        );
        client
                .reactiveExecuteQuery(www.serialize())
                .block();
    }
}
