package com.example.android.wearable.recipeassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public AppCompatButton Favorite;
    public static View RecipeView;
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
        Favorite = (AppCompatButton) view.findViewById(R.id.recipeFavoriteButton);
        RecipeView = view;
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
        if(Favorite.getVisibility() == v.GONE) {
            Favorite.setVisibility(v.VISIBLE);
        } else {
            Favorite.setVisibility(v.GONE);
        }
/*
        Context context = v.getContext();
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        context.startActivity(intent);
*/
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
    private databaseHelper databaseHelper;
    private Favorite favorite;
    private User user;

    public RecipeRecyclerAdapter(List<Recipe> listRecipe, Context context) {
        this.listRecipe = listRecipe;
        this.context = context;
    }
    private void initObjects() {
        databaseHelper = new databaseHelper(context);
        favorite = new Favorite();
        user = new User();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recipe_recycler, parent, false);
        initObjects();
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, int position) {
        holder.textViewTitle.setText(listRecipe.get(position).getTitleText());
        holder.textViewIngredients.setText(listRecipe.get(position).getIngredientsText());
        holder.textViewSummary.setText(listRecipe.get(position).getSummaryText());
        holder.textViewSteps.setText(listRecipe.get(position).getSteps());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLong) {
                R_position = listRecipe.get(position);
                if (isLong) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra("title", listRecipe.get(position).getTitleText());
                    intent.putExtra("ingredient", listRecipe.get(position).getIngredientsText());
                    intent.putExtra("summary", listRecipe.get(position).getSummaryText());
                    intent.putExtra("steps", listRecipe.get(position).getSteps());
                    context.startActivity(intent);

                }
                else{
                    // Toast.makeText(context, "Short touch", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //+ 눌렀을대 위치 -  listRecipe.get(position)
    @Override
    public int getItemCount() {
        return listRecipe.size();
    }
}