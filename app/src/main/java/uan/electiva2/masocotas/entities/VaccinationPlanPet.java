package uan.electiva2.masocotas.entities;

import android.content.ContentValues;

import uan.electiva2.masocotas.Contracts.VaccinationPlanContract;
import uan.electiva2.masocotas.Contracts.VaccinationPlanPetContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class VaccinationPlanPet {
    private int vaccinationPlanPetId;
    private int vaccinationPlanId;
    private int petId;
    private int ignored;

    public int getVaccinationPlanId() {
        return vaccinationPlanId;
    }

    public void setVaccinationPlanId(int vaccinationPlanId) {
        this.vaccinationPlanId = vaccinationPlanId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getIgnored() {
        return ignored;
    }

    public void setIgnored(int ignored) {
        this.ignored = ignored;
    }

    public int getVaccinationPlanPetId() {
        return vaccinationPlanPetId;
    }

    public void setVaccinationPlanPetId(int vaccinationPlanPetId) {
        this.vaccinationPlanPetId = vaccinationPlanPetId;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(VaccinationPlanPetContract.VaccinationPlanPetEntry.VACCINATION_PLAN_ID, vaccinationPlanId);
        values.put(VaccinationPlanPetContract.VaccinationPlanPetEntry.PET_ID, petId);
        return values;
    }
}
