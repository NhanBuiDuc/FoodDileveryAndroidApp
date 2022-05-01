package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class CartDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "cart";
    private final String first_col = "user_id";
    private final String second_col = "product_id";
    private final String third_col = "quantity";

    public CartDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long InsertCart(CartDomain cart){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, cart.getUser_id());
            contentValues.put(second_col, cart.getProduct_id());
            contentValues.put(third_col, cart.getQuantity());

            Long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("User ID is " + cart.getUser_id() +
                    " and product id = " + cart.getProduct_id() + " quantity = "
                    + cart.getQuantity() + " was inserted into table " + TABLE_NAME);
            return row;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed to table " + TABLE_NAME);
            return -1;
        }
    }
    public void DeleteCart(int product_id){

        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.beginTransaction();
        try{
            db.delete(TABLE_NAME, "user_id = ? AND product_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id()), String.valueOf(product_id)} );
            System.out.println("Delete at userid = " + CurrentUser.getUser_id() + " and product id " + product_id + " from table " + TABLE_NAME);
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed at userid = " + CurrentUser.getUser_id() + " and product id " + product_id + " from table " + TABLE_NAME);
            return;
        }
    }
    public void DeleteAllItemsInCart(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, "user_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id())});
            System.out.println("Delete all items in cart where user_id = " + CurrentUser.getUser_id());
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed in cart where user_id = " + CurrentUser.getUser_id());
            return;
        }
    }
    public ArrayList<CartDomain> SelectAllItemsInCart(){
        ArrayList<CartDomain> returnList = new ArrayList<CartDomain>();
        String[] projection = {first_col, second_col, third_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        db.beginTransaction();
        try{
            cursor = db.query(TABLE_NAME, projection, "user_id = ? ", new String[]{String.valueOf(CurrentUser.getUser_id())}, null, null, null);
            while (cursor.moveToNext()){
                int user_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);
                int quantity = cursor.getInt(2);

                System.out.println("User_id: "+ user_id);
                System.out.println("Product_id: "+ product_id);
                System.out.println("Quantity: "+ quantity);

                CartDomain cartItem = new CartDomain(user_id, product_id, quantity);
                returnList.add(cartItem);
            }
            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            db.endTransaction();
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            db.endTransaction();
            return null;
        }
    }
}
