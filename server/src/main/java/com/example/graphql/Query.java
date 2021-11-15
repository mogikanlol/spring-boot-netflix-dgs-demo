package com.example.graphql;

import com.example.test.types.Book;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@DgsComponent
public class Query {

    private final List<Book> books = List.of(
            Book.newBuilder()
                    .id(5L)
                    .title("Tetris")
                    .releaseDate(LocalDate.now())
                    .profit(BigDecimal.valueOf(24124.23))
                    .build()
    );

    @DgsData(parentType = "Query", field = "books")
    public List<Book> getBooks(DataFetchingEnvironment dataFetchingEnvironment) {
        return books;
    }

}
