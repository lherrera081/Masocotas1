package uan.electiva2.masocotas.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.UserContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class Pet {

    private int petId;
    private int userId;
    private User user;
    private String name;
    private Date birthDate;
    private String description;
    private String sex;
    private int petTypeId;
    private PetType petType;
    private Bitmap photo;

    public Pet(){}
    public Pet(Cursor cursor){
        petId = cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.PET_ID));
        name = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.NAME));
        description = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.NAME));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            birthDate = dateFormat.parse( cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.BIRTH_DATE)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        description= cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.DESCRIPTION));
        sex = cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.SEX));
        petTypeId = cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.PET_TYPE_ID));
        userId = cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.USER_ID));
    }
    public Pet(int userId, String name, Date birthDate, String description, String sex, int petTypeId, Bitmap photo){
        this.userId=userId;
        this.name=name;
        this.birthDate=birthDate;
        this.description=description;
        this.sex=sex;
        this.petTypeId=userId;
        this.photo=photo;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        if(petId > 0)
            values.put(PetContract.PetEntry.PET_ID, petId);
        values.put(PetContract.PetEntry.USER_ID, userId);
        values.put(PetContract.PetEntry.NAME, name);
        values.put(PetContract.PetEntry.SEX, sex);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        values.put(PetContract.PetEntry.BIRTH_DATE, dateFormat.format(birthDate));
        values.put(PetContract.PetEntry.DESCRIPTION, description);
        values.put(PetContract.PetEntry.PET_TYPE_ID, petTypeId);
        if(photo!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            values.put(PetContract.PetEntry.PHOTO, byteArray);
        }
        return values;
    }
}
