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
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class IntroActivity extends AppCompatActivity {
    // Views
    private ConstraintLayout startBtn;

    // DB classes
    FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    private ProductDB productDB = new ProductDB(foodyDBHelper);;
    private CategoryDB categoryDB = new CategoryDB(foodyDBHelper);;
    private FoodVariationDB food_variationDB = new FoodVariationDB(foodyDBHelper);
    private CartDB cartDB = new CartDB(foodyDBHelper);

    // Lists
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
        AutomaticInsertFoodVariation();
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
        categoryList.add(new CategoryDomain("Tea", null));
        categoryList.add(new CategoryDomain("Coffee", null));

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

        foodList.add(new FoodDomain("KFC Chicken Burger", "burger1", "The unique combination of the super soft Burger and the crispy crust of the bold Zinger creates an incredibly delicious taste. In addition, the strange harmony between Burger with Kim Chi mixed cabbage and fresh vegetables makes the dish even better.", 8.76));
        foodList.add(new FoodDomain("Lotte Cheese Burger", "burger2", "Burger believers will definitely stand still because Lotteria launches a super quality discount code on VinID Voucher. With only 36,000 VND, you will enjoy a huge Big Star Burger. What are you waiting for, access the VinID application to find out the promotional Lotteria information right away!", 2.9));
        foodList.add(new FoodDomain("Special Cheese Burger", "burger3", "SPAM Spam and eggs and noodles That’s one steamy love triangle. Sizzle Pork And Mmm REDFIN With RedFin, when you see a home you love… You can book a tour on demand, so you can see homes first. And with your local RedFin agent guiding you, the right home is closer than you think. BURGER …", 8.79));
        foodList.add(new FoodDomain("Cheese Burger", "burger4", "beef, Gouda Cheese, Special Sauce, Lettuce, tomato", 8.79));
        foodList.add(new FoodDomain("Juicy Grilled Burger", "burger5", "Beautiful combination of eggs, roasted beef and french fries", 8.79));

        foodList.add(new FoodDomain("Pepperoni pizza", "pizza1", "slices pepperoni,mozzarella cheese,fresh oregano, ground black pepper,pizza sauce", 9.76));
        foodList.add(new FoodDomain("Vegetable pizza", "pizza2", "olive oil, Vegetable oil, pitted kalamata, cherry tomatoes, fresh oregano, basil", 8.5));
        foodList.add(new FoodDomain("PizzaHut's best seller", "pizza3", "At Pizza Hut, we’re blowing the world of Hand-Tossed Pizza… sky high! Introducing our all new blow your mind Hand-Tossed Pizza", 30.5));
        foodList.add(new FoodDomain("PizzaHut's Cheese pizza", "pizza4", "For unlimited time’s right. Any hand tossed for just ten bucks. We guarantee you love it or your next pizza is on us.Go for greatness people! Pizza Hut. Make it great!", 40.5));
        foodList.add(new FoodDomain("Home made pizza", "pizza5", "Premium Traditional Large Size, or Medium Size", 8.5));

        foodList.add(new FoodDomain("HotDog Combos", "hotdog1", "8 packs of hot dogs of different taste and flavor", 50.5));
        foodList.add(new FoodDomain("HotDog and Fries", "hotdog2", "Hot dog with mustache and fry on the side", 10.5));
        foodList.add(new FoodDomain("Korean Hot Dog", "hotdog3", "Premium Pizza Korean Hot Dog", 16.5));
        foodList.add(new FoodDomain("Spicy Hot Dog", "hotdog4", "Hot flaming chili hot dog with special recipe", 30.5));
        foodList.add(new FoodDomain("Italian Hot Dog", "hotdog5", "Juicy taste with sauce on top of a burnt spicy sausage", 18.5));

        foodList.add(new FoodDomain("Taiwan Boba Tea", "drink1", "Bubble tea, tea-based drink that originated in Taiwan in the early 1980s", 10.5));
        foodList.add(new FoodDomain("Tropical Soda", "drink2", "Tropical Fruit Soda Special Drink!", 15.5));
        foodList.add(new FoodDomain("Boozy Whipped Coffee", "drink3", "Mixed coffee till it get to this beautiful shape with dairy, making a unforgettable drink", 12.5));
        foodList.add(new FoodDomain("Keto Mojito", "drink4", "Cocktail with low carb, sugar free, best luxury drink suitable for Keto diets", 18.5));
        foodList.add(new FoodDomain("Cotton Candy Margaritas", "drink5", "This is going to very quickly become a summer classic. This Cotton Candy Margarita recipe is going to be your new favorite drink!", 20.0));

        foodList.add(new FoodDomain("Boston Kreme", "donut1", "Soft and sweet, like a warm hug in your mouth, a Boston Kreme combines chocolate icing and a pudding filling. This is everything a donut can be all wrapped up into one treat. ", 15.5));
        foodList.add(new FoodDomain("Chocolate Glazed", "donut2", "It’s a glazed donut, but with chocolate. Now that’s perfection!", 10.5));
        foodList.add(new FoodDomain("French Cruller", "donut3", "The most distinct donut in terms of texture. The French cruller (which is glazed and far, far superior to a boring old plain cruller) is crunchy on the outside and flaky on the inside. A real delight for the senses. It’s also one of the most satisfying to dunk in milk. All of that plus its unbeatable taste mean I might actually have ranked it too low.", 12.5));
        foodList.add(new FoodDomain("Maple Frosted", "donut4", "The most “mature” of the frosted donut family, the maple donut is still just as sweet and wonderful as all the others icings. ", 18.5));
        foodList.add(new FoodDomain("Chocolate Glazed", "donut5", "A regular blueberry donut is fine. It’s fine. But a glazed version is the only way to go. It’s crispy yet soft, sweet yet….well it’s still really sweet even with the fruit. It’s not like the blueberries make it especially healthy, but we’re not worried about things like “calories” or “living a long life” when we’re digging through a box of donuts.", 16.5));
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

        try{
            ArrayList<CategoryDomain> categoryDomainArrayList;
            categoryDomainArrayList = categoryDB.SelectAllCategories();

            for(CategoryDomain category: categoryDomainArrayList) {
                String category_title = category.getTitle();
                for(FoodDomain product: productDB.SearchProductByKeyWord(category_title)){
                    FoodVariationDomain variation = new FoodVariationDomain(category.getId(), product.getId());
                    food_variationDB.InsertFoodVariation(variation);
                }
            }

            food_variationDB.SelectAllVariations();
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