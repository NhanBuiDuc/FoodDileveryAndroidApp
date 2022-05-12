package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class MerchantActivity extends AppCompatActivity {
    private ImageView merchantPic;
    private TextView merchantAddress;
    private RecyclerView merchantFoodList;

    //Database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
    }

    private void InitView() {
        merchantPic = findViewById(R.id.merchantPicImageView);
        merchantAddress = findViewById(R.id.merchantAddressTextView);
        merchantFoodList = findViewById(R.id.merchantFoodListRecyclerView);
    }
}