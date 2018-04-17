package com.example.android.wearable.recipeassistant;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.RecipeViewHolder> {

    private List<Recipe> listRecipe;

    public RecipeRecyclerAdapter(List<Recipe> listRecipe) {
        this.listRecipe = listRecipe;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_recycler, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.textViewTitle.setText(listRecipe.get(position).getTitleText());
        holder.textViewSummary.setText(listRecipe.get(position).getSummaryText());
        holder.textViewIngredients.setText(listRecipe.get(position).getIngredientsText());
        holder.textViewSteps.setText(listRecipe.get(position).getSteps());
    }

    @Override
    public int getItemCount() {
        return listRecipe.size();
    }
    /**
     * ViewHolder class
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewTitle;
        public AppCompatTextView textViewSummary;
        public AppCompatTextView textViewIngredients;
        public AppCompatTextView textViewSteps;


        public RecipeViewHolder(View view) {
            super(view);
            textViewTitle = (AppCompatTextView) view.findViewById(R.id.recipeTextTitle);
            textViewSummary = (AppCompatTextView) view.findViewById(R.id.recipeTextSummary);
            textViewIngredients = (AppCompatTextView) view.findViewById(R.id.recipeTextIngredients);
            textViewSteps = (AppCompatTextView) view.findViewById(R.id.recipeTextSteps);
        }
    }
}
