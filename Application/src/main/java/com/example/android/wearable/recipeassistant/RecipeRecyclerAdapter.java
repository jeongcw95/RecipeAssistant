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
                .inflate(R.layout.item_recipe_recycler, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.textViewName.setText(listRecipe.get(position).getTitleText());
        holder.textViewEmail.setText(listRecipe.get(position).getSummaryText());
        holder.textViewPassword.setText(listRecipe.get(position).getIngredientsText());
    }

    @Override
    public int getItemCount() {
        Log.v(RecipeRecyclerAdapter.class.getSimpleName(),""+listRecipe.size());
        return listRecipe.size();
    }


    /**
     * ViewHolder class
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;

        public RecipeViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
        }
    }


}
