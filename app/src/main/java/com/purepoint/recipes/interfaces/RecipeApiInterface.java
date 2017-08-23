package com.purepoint.recipes.interfaces;

import com.purepoint.recipes.model.Recipies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kasai Desktop on 23/08/2017.
 */

public interface RecipeApiInterface {
    @GET("?")
    Call<Recipies> fetchRecipies(@Query("q") String searchString ,@Query("p") int pageNumber);
}
