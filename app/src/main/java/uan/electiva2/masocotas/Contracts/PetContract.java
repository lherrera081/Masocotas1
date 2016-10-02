package uan.electiva2.masocotas.Contracts;

import android.provider.BaseColumns;

/**
 * Created by lucho on 28/9/2016.
 */
public class PetContract {
    public static abstract class PetEntry implements BaseColumns {
        public static final String TABLE_NAME ="Pets";
        public static final String PET_ID = "petId";
        public static final String USER_ID = "userId";
        public static final String NAME = "name";
        public static final String BIRTH_DATE = "birthDate";
        public static final String DESCRIPTION = "description";
        public static final String SEX = "sex";
        public static final String PET_TYPE_ID = "petTypeId";
        public static final String PHOTO = "photo";
    }
}
