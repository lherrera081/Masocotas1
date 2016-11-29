package uan.electiva2.masocotas.DataAccess;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.VaccineContract;

/**
 * Created by lucho on 28/9/2016.
 */
public class VaccinationPlanManager {

    private Context context;
    private PetsDBHelper dbHelper;
    private String[] vaccineColumns = new String[]{
            VaccineContract.VaccineEntry.VACCINE_ID +" as _id",
            VaccineContract.VaccineEntry.VACCINE_ID,
            VaccineContract.VaccineEntry.NAME,
            VaccineContract.VaccineEntry.DESCRIPTION,
    };

    public VaccinationPlanManager(Context context){
        this.context = context;
        dbHelper= new PetsDBHelper(context);
    }
    public Cursor getAllVaccines()throws SQLiteException {
        Cursor c = dbHelper.getReadableDatabase().query(
                VaccineContract.VaccineEntry.TABLE_NAME,  // Nombre de la tabla
                vaccineColumns,  // Lista de Columnas a consultar
                null,  // Columnas para la cláusula WHERE
                null,  // Valores a comparar con las columnas del WHERE
                null,  // Agrupar con GROUP BY
                null,  // Condición HAVING para GROUP BY
                null  // Cláusula ORDER BY
        );
        return c;
    }

}
