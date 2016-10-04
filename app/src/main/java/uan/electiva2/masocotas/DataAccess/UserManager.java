package uan.electiva2.masocotas.DataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import uan.electiva2.masocotas.Contracts.UserContract;
import uan.electiva2.masocotas.entities.LogicException;
import uan.electiva2.masocotas.entities.User;

/**
 * Created by lucho on 28/9/2016.
 */
public class UserManager implements Closeable {

    private Context context;
    private PetsDBHelper dbHelper;
    public UserManager(Context context){
        this.context = context;
        dbHelper= new PetsDBHelper(context);
    }
    public Cursor getAllUsers()throws SQLiteException{
        Cursor c = dbHelper.getReadableDatabase().query(
                UserContract.UserEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }

    public User getUser(String userName, String password) throws SQLiteException {
        Cursor all = getAllUsers();
        Cursor c = dbHelper.getReadableDatabase().query(
                UserContract.UserEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                UserContract.UserEntry.USER_NAME + " like ? and "+ UserContract.UserEntry.PASSWORD +" like ?",  // Columnas para la cláusula WHERE
                new String[]{ userName, password },  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        if(c.moveToNext() == false)
            return null;
        User user = new User(c);
        return user;
    }

    public User getUserByUsername(String userName) throws SQLiteException {
        Cursor c = dbHelper.getReadableDatabase().query(
                UserContract.UserEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                UserContract.UserEntry.USER_NAME + " like ?",  // Columnas para la cláusula WHERE
                new String[]{ userName },  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        if(c.moveToNext() == false)
            return null;
        User user = new User(c);
        return user;
    }
    public User insertUser(User user) throws LogicException {
        Cursor c = dbHelper.getReadableDatabase().query(
                UserContract.UserEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                UserContract.UserEntry.USER_NAME + " like ?",  // Columnas para la cláusula WHERE
                new String[]{ user.getUserName() },  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        if(c.moveToNext()==true){
            throw new LogicException(String.format("El usuario %s ya se encuentra registrado.", user.getUserName()));
        }
        user.setUserId((int) dbHelper.getWritableDatabase().insert(
                UserContract.UserEntry.TABLE_NAME,
                null,
                user.toContentValues()));
        return user;
    }

    @Override
    public void close()  {
        dbHelper.close();
    }
}
