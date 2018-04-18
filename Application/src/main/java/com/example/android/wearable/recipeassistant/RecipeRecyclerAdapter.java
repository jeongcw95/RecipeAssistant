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
class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public AppCompatTextView textViewTitle;
    public AppCompatTextView textViewIngredients;
    public AppCompatTextView textViewSummary;
    public AppCompatTextView textViewSteps;
    private ItemClickListener itemClickListener;
    public AppCompatButton Modify;
    public AppCompatButton Delete;

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

//    @Override
//    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        //        // inflating recycler item view
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View itemView = inflater.inflate(R.layout.recipe_recycler, parent, false);
//
//        return new RecipeViewHolder(itemView);
//    }

//     public void onBindViewHolder(RecipeViewHolder holder, int position) {
//        holder.textViewTitle.setText(listRecipe.get(position).getTitleText());
//        holder.textViewIngredients.setText(listRecipe.get(position).getIngredientsText());
//        holder.textViewSummary.setText(listRecipe.get(position).getSummaryText());
//        holder.textViewSteps.setText(listRecipe.get(position).getSteps());
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLong) {
//                if (isLong)
//                    Toast.makeText(context, "시발롱클릭" + listRecipe.get(position), Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(context, "조루클릭" + listRecipe.get(position), Toast.LENGTH_LONG).show();
//            }
//        });
//        return onBindViewHolder(this, position);
//    }

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
                if (isLong)
                    Toast.makeText(context, "시발롱클릭" + listRecipe.get(position), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "조루클릭" + listRecipe.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listRecipe.size();
    }
}