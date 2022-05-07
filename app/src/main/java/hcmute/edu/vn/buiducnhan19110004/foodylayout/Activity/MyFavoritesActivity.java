package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.MyFavoriteAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FavoriteDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FavoriteDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class MyFavoritesActivity extends AppCompatActivity {
    private ScrollView favoriteScrollView;
    private RecyclerView favoriteRecycleView;
    private TextView emptyTxt;
    private MyFavoriteAdapter adapter;

    //Database variables
    FoodyDBHelper foodyDBHelper;
    FavoriteDB favoriteDB;
    ArrayList<FavoriteDomain> favoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);

        InitList();
    }

    private void InitList() {
        emptyTxt = findViewById(R.id.favoriteEmptyTxt);
        favoriteScrollView = findViewById(R.id.favoriteScrollView);
        favoriteRecycleView = findViewById(R.id.favoriteRecyclerView);
        foodyDBHelper = new FoodyDBHelper(this);
        favoriteDB = new FavoriteDB(foodyDBHelper);
        favoriteList = favoriteDB.SelectFavoritesByCurrentUser();
        adapter = new MyFavoriteAdapter(foodyDBHelper, favoriteList, MyFavoritesActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteRecycleView.setLayoutManager(linearLayoutManager);
        favoriteRecycleView.setAdapter(adapter);

        if(favoriteList.size() > 0) {
            emptyTxt.setVisibility(View.GONE);
            favoriteScrollView.setVisibility(View.VISIBLE);
        }
        else {
            emptyTxt.setVisibility(View.VISIBLE);
            favoriteScrollView.setVisibility(View.GONE);
        }
    }
}