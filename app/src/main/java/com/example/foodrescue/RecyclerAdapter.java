package com.example.foodrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrescue.Data.DatabaseHelper;
import com.example.foodrescue.Model.Food;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Food> mData;
    private LayoutInflater mInflater;
    private FoodClickListener mClickListener;

    DatabaseHelper db;
    RecyclerAdapter(Context context, List<Food> data){
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        db = new DatabaseHelper(context, null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Here I switch between the layout it should use depending on if mIsVertical is true or not. (I couldn't figure out how to get access to LayoutManager.orientation.)
        View view = mInflater.inflate(R.layout.food_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        String imageUri = mData.get(position).getImageUri();
        String title = mData.get(position).getTitle();
        String description = mData.get(position).getDescription();

        if(description.length() > 16){
            holder.foodDesc.setText(String.format("%s...", description.substring(0, 55)));
        }else{
            holder.foodDesc.setText(description);
        }
        holder.foodTitle.setText(title);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        Bitmap imageVal = BitmapFactory.decodeFile(imageUri, options);
        holder.foodImage.setImageBitmap(imageVal);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView foodImage;
        TextView foodTitle;
        TextView foodDesc;

        ViewHolder(View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.foodImage);
            foodTitle = itemView.findViewById(R.id.foodTitle);
            foodDesc = itemView.findViewById(R.id.foodDesc);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    Food getItem(int id) {
        return mData.get(id);
    }


    // allows clicks events to be caught
    void setClickListener(FoodClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface FoodClickListener {
        void onItemClick(View view, int position);
    }
}
