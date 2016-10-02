package uan.electiva2.masocotas.Contracts;

import android.provider.BaseColumns;

/**
 * Created by lucho on 28/9/2016.
 */
public class PetTypeContract {
    public static abstract class PetTypeEntry implements BaseColumns {
        public static final String TABLE_NAME ="PetType";
        public static final String PET_TYPE_ID = "petTypeId";
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }
}
