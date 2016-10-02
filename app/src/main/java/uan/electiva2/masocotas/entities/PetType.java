package uan.electiva2.masocotas.entities;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.renderscript.Script;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.PetTypeContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class PetType {
    private int petTypeId;
    private String name;
    private String description;

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(PetTypeContract.PetTypeEntry.PET_TYPE_ID, petTypeId);
        values.put(PetTypeContract.PetTypeEntry.NAME, name);
        values.put(PetTypeContract.PetTypeEntry.DESCRIPTION, description);
        return values;
    }
}
