package com.example.android.wearable.recipeassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.telecom.Connection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;
import com.example.android.wearable.recipeassistant.Favorite;

class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public AppCompatTextView textViewTitle;
    public AppCompatTextView textViewIngredients;
    public AppCompatTextView textViewSummary;
    public AppCompatTextView textViewSteps;
    private ItemClickListener itemClickListener;
    public AppCompatButton Modify;
    public AppCompatButton Delete;
    public String title_name;

    public RecipeViewHolder(View view) {
        super(view);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        textViewTitle = (AppCompatTextView) view.findViewById(R.id.recipeTextTitle);
        textViewIngredients = (AppCompatTextView) view.findViewById(R.id.recipeTextIngredients);
        textViewSummary = (AppCompatTextView) view.findViewById(R.id.recipeTextSummary);
        textViewSteps = (AppCompatTextView) view.findViewById(R.id.recipeTextSteps);
        Modify = (AppCompatButton) view.findViewById(R.id.recipeModifyButton);
        Delete = (AppCompatButton) view.findViewById(R.id.recipeDeleteButton);

    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        getAdapterPosition();
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
        if(Modify.getVisibility() == v.GONE) {
            Modify.setVisibility(v.VISIBLE);
        } else {
            Modify.setVisibility(v.GONE);
        }
        if(Delete.getVisibility() == v.GONE) {
            Delete.setVisibility(v.VISIBLE);
        } else {
            Delete.setVisibility(v.GONE);
        }

    }

    @Override
    public boolean onLongClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), true);
        return false;
    }
}




public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    protected Context context;
    private List<Recipe> listRecipe;
    protected static Recipe R_position;

    public RecipeRecyclerAdapter(List<Recipe> listRecipe, Context context) {
        this.listRecipe = listRecipe;
        this.context = context;
    }
    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recipe_recycler, parent, false);

        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.textViewTitle.setText(listRecipe.get(position).getTitleText());
        holder.textViewIngredients.setText(listRecipe.get(position).getIngredientsText());
        holder.textViewSummary.setText(listRecipe.get(position).getSummaryText());
        holder.textViewSteps.setText(listRecipe.get(position).getSteps());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLong) {
                R_position = listRecipe.get(position);
                if (isLong) {
                    Toast.makeText(context, "즐겨찾기에 추가되었습니다", Toast.LENGTH_LONG).show();
                    Favorite.FAVORITE_LIST.add(R_position.getTitleText());
                }
                else{
                    // Toast.makeText(context, "Short touch", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
//+ 눌렀을때 위치 -  listRecipe.get(position)
    @Override
    public int getItemCount() {
        return listRecipe.size();
    }
}