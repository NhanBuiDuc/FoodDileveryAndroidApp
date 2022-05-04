package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.CategoryAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.PopularAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CategoryDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.*;
public class MainActivity extends AppCompatActivity {
    //Views
    TextView textViewUsername;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;

    // Adapters
    private RecyclerView.Adapter categoryAdapter, popularAdapter;

    //Lists
    private ArrayList<FoodDomain> foodList = new ArrayList<>();

    // DB classes
    private ProductDB productDB;
    private CategoryDB categoryDB;
    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetViews();
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }
    private void SetViews(){
        this.textViewUsername = findViewById(R.id.textViewUsername);
        textViewUsername.setText("Hello " + CurrentUser.getFull_name());
    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileButton = findViewById(R.id.profileBtn);
        LinearLayout supportButton = findViewById(R.id.supportBtn);
        LinearLayout settingButton = findViewById(R.id.settingbtn);
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
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUser.isIsLogin() == false){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                else {
                    // Make intent to profile activity
                }
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