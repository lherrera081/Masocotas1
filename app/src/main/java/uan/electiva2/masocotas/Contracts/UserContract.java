package uan.electiva2.masocotas.Contracts;

import android.provider.BaseColumns;

/**
 * Created by lucho on 28/9/2016.
 */
public class UserContract {

    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME ="Users";
        public static final String USER_ID = "userId";
        public static final String FIRST_NAME = "firsName";
        public static final String LAST_NAME = "LastName";
        public static final String EMAIL = "email";
        public static final String USER_NAME = "userName";
        public static final String PASSWORD = "password";
        public static final String PHOTO = "photo";
    }
}
