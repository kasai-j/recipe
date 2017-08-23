package com.purepoint.recipes.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.purepoint.recipes.R;
import com.purepoint.recipes.RecipeApiClient;
import com.purepoint.recipes.adapters.RecyclerViewAdapter;
import com.purepoint.recipes.interfaces.RecipeApiInterface;
import com.purepoint.recipes.model.Recipies;
import com.purepoint.recipes.model.Result;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    private final String TAG = RecipeActivity.class.getSimpleName();
    private final int FIRST = 1;
    private final int SECOND = 1;
    private Recipies recipeData;


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerViewAdapter mAdapter;
    private SearchView searchView;

    private RecipeApiInterface recipeApiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        setupUI();
        mAdapter = new RecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void fetchRecipes(String searchString, int pageNumber) {
        recipeApiInterface = RecipeApiClient.getClient().create(RecipeApiInterface.class);
        Call<Recipies> recipeDataCall = recipeApiInterface.fetchRecipies(searchString, pageNumber);
        recipeDataCall.enqueue(new Callback<Recipies>() {
            @Override
            public void onResponse(Call<Recipies> call, Response<Recipies> response) {
                recipeData = response.body();
                Log.d(TAG, "onResponse: " + recipeData.toString());
                ArrayList<Result> recipes = (ArrayList<Result>) recipeData.getResults();
                mAdapter.addResults(recipes);
            }

            @Override
            public void onFailure(Call<Recipies> call, Throwable t) {
                call.cancel();
                Log.d("Call failed", t.getMessage());
                Toast.makeText(RecipeActivity.this , R.string.error_retrofit_call_failure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupUI() {
        //searchview
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchRecipes(query,FIRST);
                fetchRecipes(query,SECOND);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.removeRecipes();
                fetchRecipes(newText,FIRST);
                fetchRecipes(newText,SECOND);
                return true;
            }
        });

        //recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        assert mRecyclerView != null;
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }
}
