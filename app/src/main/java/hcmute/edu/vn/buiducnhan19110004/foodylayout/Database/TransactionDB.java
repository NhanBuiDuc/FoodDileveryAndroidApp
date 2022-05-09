package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.TransactionDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class TransactionDB {
    private FoodyDBHelper dbHelper;

    private final String TABLE_NAME = "cart";
    private final String first_col = "user_id";
    private final String second_col = "product_id";
    private final String third_col = "quantity";
    private final String fourth_col = "order_date";

    public TransactionDB (FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public TransactionDomain SelectTransactionByProductID(int productId) {
        TransactionDomain transactionDomain;
        String[] projection = {first_col, second_col, third_col, fourth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor;

        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? AND product_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id()), String.valueOf(productId)}, null, null, null);
            db.endTransaction();
            cursor.moveToNext();

            int user_id = cursor.getInt(0);
            int product_id = cursor.getInt(1);
            int quantity = cursor.getInt(2);
            String transaction_time = cursor.getString(3);

            System.out.println("User_id: "+ user_id);
            System.out.println("Product_id: "+ product_id);
            System.out.println("Quantity: "+ quantity);
            System.out.println("Transaction_time: "+ transaction_time);

            transactionDomain = new TransactionDomain(user_id, product_id, quantity, transaction_time);

            System.out.println("Get transaction item of user_id = " + user_id + " and product_id = " + product_id + " data from " + TABLE_NAME + " successfully");

            return transactionDomain;
        }
        catch (SQLiteConstraintException e){
            System.out.println(e);
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }

    public ArrayList<TransactionDomain> SelectAllTransactionOfCurrentUser(){
        ArrayList<TransactionDomain> returnList = new ArrayList<TransactionDomain>();
        String[] projection = {first_col, second_col, third_col, fourth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;
        try{
            db.beginTransaction();
            cursor = db.query(TABLE_NAME, projection, "user_id = ? ", new String[]{String.valueOf(CurrentUser.getUser_id())}, null, null, null);
            db.endTransaction();
            while (cursor.moveToNext()){
                int user_id = cursor.getInt(0);
                int product_id = cursor.getInt(1);
                int quantity = cursor.getInt(2);
                String transaction_time = cursor.getString(3);

                System.out.println("User_id: "+ user_id);
                System.out.println("Product_id: "+ product_id);
                System.out.println("Quantity: "+ quantity);
                System.out.println("Transaction_time: "+ transaction_time);

                TransactionDomain transactionDomain = new TransactionDomain(user_id, product_id, quantity, transaction_time);
                returnList.add(transactionDomain);

                System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            }
            return returnList;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Get data failed from table " + TABLE_NAME);
            return null;
        }
    }
}
