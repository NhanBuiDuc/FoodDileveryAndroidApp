package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.CartListAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;

    private RecyclerView recyclerViewList;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt;
    private double tax;
    private ScrollView scrollView;
    private TextView textViewCheckOut;

    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    private CartDB cartDB = new CartDB(foodyDBHelper);
    private ArrayList<CartDomain> cartDomainArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        initView();
        initList();
        CalculateCart();
        bottomNavigation();
    }

    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartListActivity.this, MainActivity.class));
            }
        });
    }

    private void initView() {
        recyclerViewList = findViewById(R.id.recyclerViewCategories);
        totalFeeTxt = findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt = findViewById(R.id.deliveryTxt);
        totalTxt = findViewById(R.id.totalTxt);
        emptyTxt = findViewById(R.id.emptyTxt);
        scrollView = findViewById(R.id.scrollViewMain);
        recyclerViewList=findViewById(R.id.cartViewCartList);
        textViewCheckOut = findViewById(R.id.textViewCheckOut);
        textViewCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double percentTax = 0.02;
                double delivery = 10;

                double tax = Math.round((cartDB.CalculateCart() * percentTax) * 100) / 100;
                double total = Math.round((cartDB.CalculateCart() + tax + delivery) * 100) / 100;
                double itemTotal = Math.round(cartDB.CalculateCart() * 100) / 100;

                Intent intent = new Intent(CartListActivity.this, CheckOutActivity.class);
                intent.putExtra("sumAllItems", itemTotal);
                intent.putExtra("deliveryFee", delivery);
                intent.putExtra("tax", tax);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
    }

    private void initList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        cartDomainArrayList = cartDB.SelectAllItemsInCartOfCurrentUser();
        adapter = new CartListAdapter(foodyDBHelper, cartDomainArrayList, CartListActivity.this);

        recyclerViewList.setAdapter(adapter);

        if(cartDomainArrayList.size() < 1){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }
        else {
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }

    public void CalculateCart() {
        if(cartDB.CountCart() > 0){
            double percentTax = 0.02;
            double delivery = 10;

            double tax = Math.round((cartDB.CalculateCart() * percentTax) * 100) / 100;
            double total = Math.round((cartDB.CalculateCart() + tax + delivery) * 100) / 100;
            double itemTotal = Math.round(cartDB.CalculateCart() * 100) / 100;

            totalFeeTxt.setText("$" + itemTotal);
            taxTxt.setText("$" + tax);
            deliveryTxt.setText("$" + delivery);
            totalTxt.setText("$" + total);
        }
        else{
            totalFeeTxt.setText("$" + 0);
            taxTxt.setText("$" + 0);
            deliveryTxt.setText("$" + 0);
            totalTxt.setText("$" + 0);
        }
    }
}