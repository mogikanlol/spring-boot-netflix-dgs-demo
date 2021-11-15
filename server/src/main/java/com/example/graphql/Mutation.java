package com.example.graphql;

import com.example.test.types.Game;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;

import java.util.List;

@DgsComponent
public class Mutation {

    @DgsData(parentType = "Mutation", field = "test")
    public String getGames(@InputArgument("games") List<Game> games) {
        System.out.println(games);
        return null;
    }

}
