package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FavoriteDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;

public class FavoriteDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "favorite";
    private final String first_col = "user_id";
    private final String second_col = "product_id";

    public FavoriteDB(FoodyDBHelper dBHelper) {
        this.dbHelper = dBHelper;
    }

    public long InsertFavorite(FavoriteDomain favoriteDomain) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(first_col, favoriteDomain.getUser_id());
            contentValues.put(second_col, favoriteDomain.getProduct_id());

            db.beginTransaction();
            Long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();

            System.out.println("User ID is " + favoriteDomain.getUser_id() +
                    " and product id = " + favoriteDomain.getProduct_id() + " was inserted into table " + TABLE_NAME);

            return row;
        }
        catch (SQLiteConstraintException e){
            System.out.println("Insert failed to table " + TABLE_NAME);
            System.out.println(e);

            return -1;
        }
    }

    public ArrayList<FavoriteDomain> SelectAllFavorites() {
        ArrayList<FavoriteDomain> returnList = new ArrayList<FavoriteDomain>();
        String[] projection = {first_col, second_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            while(cursor.moveToNext()) {
                int userId = cursor.getInt(0);
                int productId = cursor.getInt(1);

                System.out.println("User ID:" + userId);
                System.out.println("Product ID:" + productId);

                FavoriteDomain favoriteDomain = new FavoriteDomain(userId, productId);
                returnList.add(favoriteDomain);
            }

            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            db.endTransaction();

            return returnList;
        }
        catch (SQLiteException e) {
            System.out.println("Get data failed from table " + TABLE_NAME);
            db.endTransaction();

            return null;
        }
    }

    public ArrayList<FavoriteDomain> SelectFavoritesByCurrentUser() {
        ArrayList<FavoriteDomain> returnList = new ArrayList<FavoriteDomain>();
        String[] projection = {first_col, second_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, projection, "user_id = ?", new String[]{String.valueOf(CurrentUser.getUser_id())}, null, null, null);
            while(cursor.moveToNext()) {
                int userId = cursor.getInt(0);
                int productId = cursor.getInt(1);

                System.out.println("User ID:" + userId);
                System.out.println("Product ID:" + productId);

                FavoriteDomain favoriteDomain = new FavoriteDomain(userId, productId);
                returnList.add(favoriteDomain);
            }

            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            db.endTransaction();

            return returnList;
        }
        catch (SQLiteException e) {
            System.out.println("Get data failed from table " + TABLE_NAME);
            db.endTransaction();

            return null;
        }
    }

    public void DeleteAllFavorites() {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all cart item of all users");

        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed");
            return;
        }
    }

    public void DeleteFavoriteByProductId(int productId) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        try{
            db.delete(TABLE_NAME, "product_id = ?", new String[]{String.valueOf(productId)} );
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed at product_id = " + productId + " from table " + TABLE_NAME);
            return;
        }
    }
}