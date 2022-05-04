package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.CheckOutAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class CheckOutActivity extends AppCompatActivity {
    // Views
    RecyclerView recyclerViewCheckOut;
    TextView CheckOut_textViewTotal, CheckOut_textViewOrder;

    // Adapter
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        setViews();
        getBundle();
        initCartList();
    }
    private void setViews() {
        recyclerViewCheckOut = findViewById(R.id.recyclerViewCheckOut);
        CheckOut_textViewTotal = findViewById(R.id.CheckOut_textViewTotal);
        CheckOut_textViewOrder = findViewById(R.id.CheckOut_textViewOrder);
        CheckOut_textViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void getBundle(){
        double total = getIntent().getDoubleExtra("total", 0);
        total = Math.round(total);
        CheckOut_textViewTotal.setText( "$ " + String.valueOf(total));
    }
    private void initCartList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCheckOut.setLayoutManager(linearLayoutManager);

        adapter = new CheckOutAdapter(CheckOutActivity.this);
        recyclerViewCheckOut.setAdapter(adapter);
    }
}
