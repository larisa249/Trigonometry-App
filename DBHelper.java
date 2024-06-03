package com.example.trigo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Trigo.db";

    public DBHelper(Context context) {
        super(context, "Trigo.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table utilizatori(username TEXT primary key, password TEXT, varsta int)");
        myDB.execSQL("CREATE TABLE istoric(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, expresie TEXT, FOREIGN KEY(username) REFERENCES utilizatori(username))");
        myDB.execSQL("CREATE TABLE scores(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, score INT, FOREIGN KEY(username) REFERENCES utilizatori(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists utilizatori");
        myDB.execSQL("DROP TABLE IF EXISTS istoric");
        myDB.execSQL("DROP TABLE IF EXISTS scores");
        onCreate(myDB);
    }

    public Boolean insertData(String username, String password, int varsta){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("varsta", varsta);
        long result = myDB.insert("utilizatori", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from utilizatori where username = ?", new String[]{username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from utilizatori where username = ? and password = ?", new String[]{username,password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public ArrayList<String> getAllUsers() {
        ArrayList<String> userList = new ArrayList<>();
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM utilizatori", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int usernameIndex = cursor.getColumnIndex("username");
                int passwordIndex = cursor.getColumnIndex("password");
                int varstaIndex = cursor.getColumnIndex("varsta");

                if (usernameIndex != -1 && passwordIndex != -1 && varstaIndex != -1) {
                    String username = cursor.getString(usernameIndex);
                    String password = cursor.getString(passwordIndex);
                    int varsta = cursor.getInt(varstaIndex);
                    userList.add("Username: " + username + ", Password: " + password + ", Varsta: " + varsta);
                }
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return userList;
    }

    public boolean insertExpression(String username, String expresie) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("expresie", expresie);
        long result = myDB.insert("istoric", null, contentValues);
        if(result == -1) {
            Log.e("DBHelper", "Inserare esuata pentru utilizatorul: " + username);
        } else {
            Log.d("DBHelper", "Expresie inserata cu succes pentru utilizatorul: " + username);
        }
        return result != -1;
    }


    public ArrayList<String> getUserHistory(String username) {
        ArrayList<String> history = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT expresie FROM istoric WHERE username = ?", new String[]{username});
            int expresieIndex = cursor.getColumnIndex("expresie");
            if (expresieIndex != -1) {
                while (cursor.moveToNext()) {
                    history.add(cursor.getString(expresieIndex));
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.e("DBHelper", "Nu se poate prelua istoricul", e);
        }
        return history;
    }

    public boolean updateUsername(String currentUsername, String newUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues userValues = new ContentValues();
            userValues.put("username", newUsername);
            int updateUsers = db.update("utilizatori", userValues, "username = ?", new String[]{currentUsername});

            ContentValues historyValues = new ContentValues();
            historyValues.put("username", newUsername);
            int updateHistory = db.update("istoric", historyValues, "username = ?", new String[]{currentUsername});

            if (updateUsers > 0 || updateHistory > 0) {
                db.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        } finally {
            db.endTransaction();
        }
    }


    public boolean updateParola(String parolaVeche, String parolaNoua) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues userValues = new ContentValues();
            userValues.put("password", parolaNoua);
            int updateUsers = db.update("utilizatori", userValues, "password = ?", new String[]{parolaVeche});

            if (updateUsers > 0) {
                db.setTransactionSuccessful();
                return true;
            } else {
                return false;
            }
        } finally {
            db.endTransaction();
        }
    }


    public boolean deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deleteHistory = db.delete("istoric", "username = ?", new String[]{username});
        int deleteUser = db.delete("utilizatori", "username = ?", new String[]{username});
        return deleteUser > 0;
    }








}
