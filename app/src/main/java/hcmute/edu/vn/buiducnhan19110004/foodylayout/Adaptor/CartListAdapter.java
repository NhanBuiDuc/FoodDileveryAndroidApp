package hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.ShowDetailActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.ManagementCart;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Interface.ChangeNumberItemsListener;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<FoodDomain> foodDomains;
    private ArrayList<CartDomain> cartDomains;

    private ChangeNumberItemsListener changeNumberItemsListener;
    FoodyDBHelper foodyDBHelper;
    private CartDB cartDB;
    private ProductDB productDB;

    public CartListAdapter(ChangeNumberItemsListener changeNumberItemsListener, FoodyDBHelper foodyDBHelper) {

        this.changeNumberItemsListener = changeNumberItemsListener;
        this.foodyDBHelper = foodyDBHelper;
        this.cartDB = new CartDB(foodyDBHelper);
        this.productDB = new ProductDB(foodyDBHelper);
        this.cartDomains = cartDB.SelectAllItemsInCart();

        for(CartDomain cartItem: cartDomains){
            FoodDomain food = new FoodDomain();
            food = productDB.SelectProductByID(cartItem.getProduct_id());
            this.foodDomains.add(food);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(foodDomains.get(position).getTitle());
        holder.feeEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getNumberInCart() * foodDomains.get(position).getFee()) * 100) / 100));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberInCart()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic()
                , "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.plusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                managementCart.plusNumberFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        //notifyItemInserted(position);
                        notifyDataSetChanged();
                        //changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.minusItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managementCart.minusNumberFood(foodDomains, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        //notifyDataSetChanged();
                        notifyItemRemoved(position);
                        //changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            num = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
