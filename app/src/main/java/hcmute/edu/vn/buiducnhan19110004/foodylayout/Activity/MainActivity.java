package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.CategoryAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.PopularAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CategoryDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.*;
public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter categoryAdapter, popularAdapter;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private ArrayList<FoodDomain> foodList = new ArrayList<>();
    private ProductDB productDB;
    private CategoryDB categoryDB;
    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        categoryDB = new CategoryDB(foodyDBHelper);

        ArrayList<CategoryDomain> categoryList = categoryDB.SelectAllCategories();

        categoryAdapter = new CategoryAdaptor(categoryList);
        recyclerViewCategoryList.setAdapter(categoryAdapter);
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerViewPopular);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        productDB = new ProductDB(foodyDBHelper);

        foodList = productDB.SelectAllProducts();

        popularAdapter = new PopularAdaptor(foodList, MainActivity.this);
        recyclerViewPopularList.setAdapter(popularAdapter);
    }
}