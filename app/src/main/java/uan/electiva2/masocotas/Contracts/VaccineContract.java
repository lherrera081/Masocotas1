package uan.electiva2.masocotas.Contracts;

import android.provider.BaseColumns;

/**
 * Created by lucho on 28/9/2016.
 */
public class VaccineContract {
    public static abstract class VaccineEntry implements BaseColumns {
        public static final String TABLE_NAME ="Vaccine";
        public static final String VACCINE_ID = "vaccineId";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }
}
