package com.purepoint.recipes.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.purepoint.recipes.R;
import com.purepoint.recipes.model.Result;

import java.util.ArrayList;

/**
 * Created by Kasai Desktop on 23/08/2017.
 * Adapter for the Recyclerview
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecipeHolder> {

private ArrayList<Result> mRecipeData;

public RecyclerViewAdapter(){
        this.mRecipeData = new ArrayList<>();
        }


@Override
public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflatedView = LayoutInflater.from(parent.getContext());
        return  new RecipeHolder(inflatedView.inflate(R.layout.recipe_list_item, parent,false));
        }

@Override
public void onBindViewHolder(RecipeHolder holder, int position) {
        holder.bindData(position);
        }

@Override
public int getItemCount() {
        return mRecipeData.size();
        }

        public void addResults(ArrayList<Result> recipeData){

            mRecipeData.addAll(recipeData);
            notifyDataSetChanged();
        }

        public void removeRecipes(){
            mRecipeData.removeAll(mRecipeData);
        }


public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView mTitle;


    public RecipeHolder(View v){
        super(v);
        mTitle = (TextView) v.findViewById(R.id.textViewTitle);
        v.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
       //Not implemented as per the requirements.
    }

    public void bindData(int position) {
        mTitle.setText(mRecipeData.get(position).getTitle());
    }
}
}
