package uan.electiva2.masocotas.entities;

import android.content.ContentValues;

import uan.electiva2.masocotas.Contracts.VaccinationPlanContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class VaccinationPlan {
    private int vaccinationPlanId;
    private int vaccineId;
    private int petTypeId;
    private int ageInWeeks;

    public int getVaccinationPlanId() {
        return vaccinationPlanId;
    }

    public void setVaccinationPlanId(int vaccinationPlanId) {
        this.vaccinationPlanId = vaccinationPlanId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public int getAgeInWeeks() {
        return ageInWeeks;
    }

    public void setAgeInWeeks(int ageInWeeks) {
        this.ageInWeeks = ageInWeeks;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(VaccinationPlanContract.VaccinationPlanEntry.VACCINATION_PLAN_ID, vaccinationPlanId);
        values.put(VaccinationPlanContract.VaccinationPlanEntry.VACCINE_ID, vaccineId);
        values.put(VaccinationPlanContract.VaccinationPlanEntry.PET_TYPE_ID, petTypeId);
        values.put(VaccinationPlanContract.VaccinationPlanEntry.AGE_IN_WEEKS, ageInWeeks);
        return values;
    }
}
