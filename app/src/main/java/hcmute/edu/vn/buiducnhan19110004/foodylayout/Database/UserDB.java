package hcmute.edu.vn.buiducnhan19110004.foodylayout.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;

public class UserDB {
    private FoodyDBHelper dbHelper;
    private final String TABLE_NAME = "user";
    private final String first_col = "id";
    private final String second_col = "fullname";
    private final String third_col = "email";
    private final String fourth_col = "password";
    private final String fifth_col = "phone";

    public UserDB(FoodyDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public boolean CheckDuplicateEmail(UserDomain userDomain) {
        ArrayList<UserDomain> allUsers = SelectALlUsers();
        for (UserDomain user : allUsers) {
            if(userDomain.getEmail().equals(user.getEmail()))
                return true;
        }

        return false;
    }

    public long InsertUser(UserDomain userDomain) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.putNull(first_col);
            contentValues.put(second_col, userDomain.getFull_name());
            contentValues.put(third_col, userDomain.getEmail());
            contentValues.put(fourth_col, userDomain.getPassword());
            contentValues.put(fifth_col, userDomain.getPhone());

            long row = db.insertOrThrow(TABLE_NAME, null, contentValues);
            db.setTransactionSuccessful();
            db.endTransaction();
            System.out.println("ID is " + row + " was inserted into table " + TABLE_NAME);

            return row;
        }
        catch (SQLiteConstraintException e) {
            System.out.println("Insert failed to table " + TABLE_NAME);

            return -1;
        }
    }

    public ArrayList<UserDomain> SelectALlUsers() {
        ArrayList<UserDomain> returnList = new ArrayList<UserDomain>();
        String[] projection = {first_col, second_col, third_col, fourth_col, fifth_col};
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor;

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String fullname = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);
                String phone = cursor.getString(4);

                System.out.println("ID: " + id);
                System.out.println("Fullname: " + fullname);
                System.out.println("Email: " + email);
                System.out.println("Password: " + password);
                System.out.println("Phone: " + phone);

                UserDomain userDomain = new UserDomain(id, fullname, email, phone, password);
                returnList.add(userDomain);
            }

            System.out.println("Get " + returnList.size() + " rows of data from " + TABLE_NAME + " successfully");
            db.endTransaction();

            return returnList;
        }
        catch (SQLiteConstraintException e) {
            System.out.println("Get data failed from table " + TABLE_NAME);
            db.endTransaction();

            return null;
        }
    }

    public void DeleteUser(int id) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
            System.out.println("Delete at id" + id + " from table " + TABLE_NAME);
        }
        catch (SQLiteException e) {
            System.out.println("Delete failed at id " + id + " from table " + TABLE_NAME);
            return;
        }
    }

    public void DeleteAllUsers(){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        try{
            db.delete(TABLE_NAME, null, null);
            System.out.println("Delete all categories");
        }
        catch (SQLiteConstraintException e){
            System.out.println("Delete failed");
            return;
        }
    }
}
