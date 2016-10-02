package uan.electiva2.masocotas.DataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.io.Closeable;
import java.io.IOException;

import uan.electiva2.masocotas.Contracts.PetContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class PetManager implements Closeable {
    private Context context;
    private PetsDBHelper dbHelper;
    private String[] petColumns = new String[]{
            "petId as _id",
            "petId",
             "userId",
             "name",
             "birthDate",
             "description",
             "sex",
             "petTypeId",
             "photo"
    };
    public PetManager(Context context){
        this.context = context;
        dbHelper= new PetsDBHelper(context);
    }
    public Cursor getAllPets()throws SQLiteException {
        Cursor c = dbHelper.getReadableDatabase().query(
                PetContract.PetEntry.TABLE_NAME,  // Nombre de la tabla
                petColumns,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }

    public PetsDBHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    public void close() {
        dbHelper.close();
    }
}
