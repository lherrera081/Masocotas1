package uan.electiva2.masocotas.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import uan.electiva2.masocotas.Contracts.UserContract;

/**
 * Created by lucho on 3/9/2016.
 */
public class User {

    private int userId;
    private String name;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Bitmap photo;

    public User() {
    }

    public User(Cursor c){
        this.userId=c.getInt(c.getColumnIndex(UserContract.UserEntry.USER_ID));
        this.name=c.getString(c.getColumnIndex(UserContract.UserEntry.FIRST_NAME));
        this.lastName=c.getString(c.getColumnIndex(UserContract.UserEntry.LAST_NAME));
        this.email=c.getString(c.getColumnIndex(UserContract.UserEntry.EMAIL));
        this.userName=c.getString(c.getColumnIndex(UserContract.UserEntry.USER_NAME));
        this.password=c.getString(c.getColumnIndex(UserContract.UserEntry.PASSWORD));
        byte[] bitmapdata = c.getBlob(c.getColumnIndex(UserContract.UserEntry.PHOTO));
        if(bitmapdata!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            this.photo = bitmap;
        }
    }

    public User(String name, String lastName, String email, String userName, String password, Bitmap photo){
        this.name=name;
        this.lastName=lastName;
        this.email=email;
        this.userName=userName;
        this.password=password;
        this.photo=photo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String user) {
        this.userName = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(UserContract.UserEntry.USER_ID, userId);
        values.put(UserContract.UserEntry.FIRST_NAME, name);
        values.put(UserContract.UserEntry.LAST_NAME, lastName);
        values.put(UserContract.UserEntry.EMAIL, email);
        values.put(UserContract.UserEntry.USER_NAME, userName);
        values.put(UserContract.UserEntry.PASSWORD, password);
        if(photo!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            values.put(UserContract.UserEntry.PHOTO, byteArray);
        }
        return values;
    }
}
