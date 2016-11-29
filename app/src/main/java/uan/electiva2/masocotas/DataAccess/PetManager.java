package uan.electiva2.masocotas.DataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.PetTypeContract;
import uan.electiva2.masocotas.Contracts.UserContract;
import uan.electiva2.masocotas.entities.Pet;
import uan.electiva2.masocotas.entities.PetType;
import uan.electiva2.masocotas.entities.User;

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
    public Cursor getPetsByUser(String userId)throws SQLiteException {
        Cursor c = dbHelper.getReadableDatabase().query(
                PetContract.PetEntry.TABLE_NAME,  // Nombre de la tabla
                petColumns,  // Lista de Columnas a consultar
                PetContract.PetEntry.USER_ID + " = ?",  // Columnas para la cláusula WHERE
                new String[]{ userId},  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // CláusulaW ORDER BY
        );
        return c;
    }
    public Pet getPetById(String id) throws SQLiteException {
        Cursor c = dbHelper.getReadableDatabase().query(
                PetContract.PetEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                PetContract.PetEntry.PET_ID + " = ?",  // Columnas para la cláusula WHERE
                new String[]{ id },  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        if(c.moveToNext() == false)
            return null;
        Pet pet = new Pet(c);
        if(pet==null)
            return null;
        PetType petType = getPetTypeById(pet.getPetTypeId());
        if(petType!= null)
            pet.setPetType(petType);
        return pet;
    }

    public int deletePet(String id)  {
        int c = dbHelper.getWritableDatabase().delete(
                PetContract.PetEntry.TABLE_NAME,  // Nombre de la tabla
                PetContract.PetEntry.PET_ID + " = ?",  // Columnas para la cláusula WHERE
                new String[]{ id }  // Valores a comparar con las columnas del WHERE
        );
        return c;
    }

    public int UpdatePet(Pet entity)  {
        String petId = Integer.toString(entity.getPetId());
        int c = dbHelper.getWritableDatabase().update(
                PetContract.PetEntry.TABLE_NAME,  // Nombre de la tabla
                entity.toContentValues(),// Valores a comparar con las columnas del WHERE
                PetContract.PetEntry.PET_ID + " = ?",  // Columnas para la cláusula WHERE
                new String[]{ String.valueOf(entity.getPetId())  }  // Valores a comparar con las columnas del WHERE
        );
        return c;
    }
    public int InsertPet(Pet entity) {
        int c =(int)dbHelper.getWritableDatabase().insert(PetContract.PetEntry.TABLE_NAME, null,entity.toContentValues());
        return c;
    }
    public PetType[] getPetTypes()throws SQLiteException {
        ArrayList<PetType> petTypes = new ArrayList<PetType>();
        Cursor cursor = dbHelper.getReadableDatabase().query(
                PetTypeContract.PetTypeEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            petTypes.add(new PetType(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return petTypes.toArray(new PetType[petTypes.size()]);
    }

    public PetType getPetTypeById(int id)throws SQLiteException {
        PetType petType = null;
        Cursor cursor = dbHelper.getReadableDatabase().query(
                PetTypeContract.PetTypeEntry.TABLE_NAME,  // Nombre de la tabla
                null,  // Lista de Columnas a consultar
                PetTypeContract.PetTypeEntry.PET_TYPE_ID + " = ?",  // Columnas para la cláusula WHERE
                new String[]{ String.valueOf(id) },  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        if(cursor.moveToNext()) {
            petType = new PetType(cursor);
        }
        cursor.close();
        return petType;
    }

    public PetsDBHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    public void close() {

        //dbHelper.close();
    }
}
