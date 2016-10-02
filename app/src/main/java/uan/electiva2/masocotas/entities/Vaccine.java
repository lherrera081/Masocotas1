package uan.electiva2.masocotas.entities;

import android.content.ContentValues;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.PetTypeContract;
import uan.electiva2.masocotas.Contracts.VaccineContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class Vaccine {
    private int vaccineId;
    private String name;
    private String description;

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

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(VaccineContract.VaccineEntry.VACCINE_ID, vaccineId);
        values.put(VaccineContract.VaccineEntry.NAME, name);
        values.put(VaccineContract.VaccineEntry.DESCRIPTION, description);
        return values;
    }
}
