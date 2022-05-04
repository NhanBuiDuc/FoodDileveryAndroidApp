package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CategoryDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodVariationDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodVariationDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class IntroActivity extends AppCompatActivity {
    private ConstraintLayout startBtn;
    FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    private ProductDB productDB = new ProductDB(foodyDBHelper);;
    private CategoryDB categoryDB = new CategoryDB(foodyDBHelper);;
    private FoodVariationDB food_variationDB;
    private CartDB cartDB = new CartDB(foodyDBHelper);
    ArrayList<FoodDomain> foodList = new ArrayList<>();
    ArrayList<CategoryDomain> categoryList = new ArrayList<>();
    ArrayList<FoodVariationDomain> food_variationList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //SetCurrentUser();
        InsertFood();
        InsertCategory();
        // AutomaticInsertFoodVariation();
        DeleteAllCart();

        startBtn=findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void InsertCategory(){
        categoryDB.DeleteAllCategories();
        categoryDB.SelectAllCategories();
        categoryList.add(new CategoryDomain("Pizza", "cat_1"));
        categoryList.add(new CategoryDomain("Burger", "cat_2"));
        categoryList.add(new CategoryDomain("Hot dog", "cat_3"));
        categoryList.add(new CategoryDomain("Drink", "cat_4"));
        categoryList.add(new CategoryDomain("Donut", "cat_5"));
        for(CategoryDomain category: categoryList){
            categoryDB.InsertCategory(category);
        }
        categoryDB.SelectAllCategories();
    }
    private void InsertFood(){
        try{
            productDB.DeleteAllProducts();
        }
        catch (Exception e){
            System.out.println(e);
        }
        productDB.SelectAllProducts();
        foodList.add(new FoodDomain("Pepperoni pizza", "pizza1", "slices pepperoni,mozzarella cheese,fresh oregano, ground black pepper,pizza sauce", 9.76));
        foodList.add(new FoodDomain("Cheese Burger", "burger", "beef, Gouda Cheese, Special Sauce, Lettuce, tomato", 8.79));
        foodList.add(new FoodDomain("Vegetable pizza", "pizza2", "olive oil, Vegetable oil, pitted kalamata, cherry tomatoes, fresh oregano, basil", 8.5));
        for(FoodDomain food: foodList){
            productDB.InsertProduct(food);
        }

        productDB.SelectAllProducts();
    }
    private void AutomaticInsertFoodVariation(){
        try{
            food_variationDB.DeleteAllVariations();
        }
        catch (Exception e){
            System.out.println(e);
        }
        food_variationDB.SelectAllVariations();

        try{
            ArrayList<CategoryDomain> categoryDomainArrayList;
            categoryDomainArrayList = categoryDB.SelectAllCategories();

            for(CategoryDomain category: categoryDomainArrayList) {
                String category_title = category.getTitle();
                for(FoodDomain product: productDB.SearchProductByName(category_title)){
                    food_variationList.add(new FoodVariationDomain(category.getId(), product.getId()));
                }
            }
            for(FoodVariationDomain variation: food_variationList){
                food_variationDB.InsertFoodVariation(variation);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    private void DeleteAllCart(){
        cartDB.ClearCartTable();

        cartDB.SelectAllItemsInCartOfAllUsers();
    }
}