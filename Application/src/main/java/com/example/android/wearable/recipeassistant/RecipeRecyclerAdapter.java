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
    private OnItemClickListener onItemClickListener;


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
        holder.textViewIngredients.setText(listRecipe.get(position).getIngredientsText());
        holder.textViewSummary.setText(listRecipe.get(position).getSummaryText());
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
        public AppCompatTextView textViewIngredients;
        public AppCompatTextView textViewSummary;
        public AppCompatTextView textViewSteps;


        public RecipeViewHolder(View view) {
            super(view);
            textViewTitle = (AppCompatTextView) view.findViewById(R.id.recipeTextTitle);
            textViewIngredients = (AppCompatTextView) view.findViewById(R.id.recipeTextIngredients);
            textViewSummary = (AppCompatTextView) view.findViewById(R.id.recipeTextSummary);
            textViewSteps = (AppCompatTextView) view.findViewById(R.id.recipeTextSteps);
        }
    }

    interface OnItemClickListener {
        void onItemClickListener(View view, int position);
    }
    private void performOnItemClicked(View v, int p) {
        onItemClickListener.onItemClickListener(v, p);
    }

}
