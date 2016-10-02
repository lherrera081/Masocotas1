package uan.electiva2.masocotas.Contracts;

import android.provider.BaseColumns;

/**
 * Created by lucho on 28/9/2016.
 */
public class VaccinationPlanPetContract {
    public static abstract class VaccinationPlanPetEntry implements BaseColumns {
        public static final String TABLE_NAME ="VaccinationPlanPets";
        public static final String VACCINATION_PLAN_PET_ID = "vaccinationPlanPetId";
        public static final String PET_ID = "petId";
        public static final String VACCINATION_PLAN_ID = "vaccinationPlanId";
        public static final String IGNORED = "ignored";
    }
}
