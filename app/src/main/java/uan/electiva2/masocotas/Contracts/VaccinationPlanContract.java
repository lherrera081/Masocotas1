package uan.electiva2.masocotas.Contracts;

import android.provider.BaseColumns;

/**
 * Created by lucho on 28/9/2016.
 */
public class VaccinationPlanContract {
    public static abstract class VaccinationPlanEntry implements BaseColumns {
        public static final String TABLE_NAME ="VaccinationPlan";
        public static final String VACCINATION_PLAN_ID = "vaccinationPlanId";
        public static final String PET_TYPE_ID = "petTypeId";
        public static final String VACCINE_ID = "vaccineId";
        public static final String AGE_IN_WEEKS = "ageInWeeks";
    }
}
