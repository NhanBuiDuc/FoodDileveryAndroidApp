package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class CheckOutActivity extends AppCompatActivity {
    RecyclerView recyclerViewCheckOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setViews();
    }
    private void setViews() {
        recyclerViewCheckOut = findViewById(R.id.recyclerViewCheckOut);
    }
}
