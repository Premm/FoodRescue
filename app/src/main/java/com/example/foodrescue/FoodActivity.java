package com.example.foodrescue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.foodrescue.Data.DatabaseHelper;
import com.example.foodrescue.Model.Food;
import com.example.foodrescue.Model.User;

import java.util.ArrayList;
import java.util.List;

public class FoodActivity extends AppCompatActivity implements RecyclerAdapter.FoodClickListener  {

    DatabaseHelper db;
    List<Food> items;
    RecyclerAdapter adapter;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("USER");
        db = new DatabaseHelper(this, null);
        items = db.fetchAllFood();
        RecyclerView notesRecyclerView = findViewById(R.id.foodRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, items);
        adapter.setClickListener((RecyclerAdapter.FoodClickListener)this);
        notesRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        items = db.fetchAllFood();
        RecyclerView notesRecyclerView = findViewById(R.id.foodRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerAdapter(this, items);
        adapter.setClickListener((RecyclerAdapter.FoodClickListener)this);
        notesRecyclerView.setAdapter(adapter);
    }

    public void popupMenu(View view) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.popup_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle().equals("My List")) {
                    List<Food> tempList = db.fetchUserFood(String.valueOf(user.getId()));
                    items.clear();
                    items.addAll(tempList);
                }else{
                    items.clear();
                    items.addAll(db.fetchAllFood());
                }
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        popup.show(); //showing popup menu
    }

    public void addFood(View view) {
        Intent intent = new Intent(this, AddFood.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        db.insertUserFood(String.valueOf(user.getId()), String.valueOf(items.get(position).getId()));
        Toast.makeText(this, "An item was added to your list.", Toast.LENGTH_SHORT).show();
    }
}