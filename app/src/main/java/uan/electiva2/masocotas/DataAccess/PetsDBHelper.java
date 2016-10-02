package uan.electiva2.masocotas.DataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.Contracts.PetTypeContract;
import uan.electiva2.masocotas.Contracts.UserContract;
import uan.electiva2.masocotas.Contracts.VaccinationPlanContract;
import uan.electiva2.masocotas.Contracts.VaccinationPlanPetContract;
import uan.electiva2.masocotas.Contracts.VaccineContract;
import uan.electiva2.masocotas.entities.Pet;
import uan.electiva2.masocotas.entities.PetType;
import uan.electiva2.masocotas.entities.User;
import uan.electiva2.masocotas.entities.VaccinationPlan;
import uan.electiva2.masocotas.entities.Vaccine;


/**
 * Created by lucho on 28/9/2016.
 */
public class PetsDBHelper extends SQLiteOpenHelper {


    ///Versión 1 - Vacunas para perros
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "Pets.db";

    public PetsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        _dropAllTables(sqLiteDatabase);
        //Create tables
        //
        _createUserTable(sqLiteDatabase);
        _createPetTypeTable(sqLiteDatabase);
        _createVaccineTable(sqLiteDatabase);
        _createPetTable(sqLiteDatabase);
        _createVaccinationPlanTable(sqLiteDatabase);
        _createVaccinationPlanPetsTable(sqLiteDatabase);
        //Insert default data
        //
        _InsertDefaultUser(sqLiteDatabase);
        _InsertVaccinationPlans(sqLiteDatabase);
    }

    private void _dropAllTables(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL("DROP TABLE " + VaccinationPlanPetContract.VaccinationPlanPetEntry.TABLE_NAME);
        }catch (SQLiteException e){
           e.printStackTrace();
        }
        try {
            sqLiteDatabase.execSQL("DROP TABLE " + VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME );
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        try {
            sqLiteDatabase.execSQL("DROP TABLE " + PetTypeContract.PetTypeEntry.TABLE_NAME );
        }catch (SQLiteException e){
            e.printStackTrace();
        }
        try {
            sqLiteDatabase.execSQL("DROP TABLE " + UserContract.UserEntry.TABLE_NAME );
        }catch (SQLiteException e){
        e.printStackTrace();
        }
        try {
            sqLiteDatabase.execSQL("DROP TABLE " + PetContract.PetEntry.TABLE_NAME );
        }catch (SQLiteException e){
        e.printStackTrace();
        }
        try {
            sqLiteDatabase.execSQL("DROP TABLE " + VaccineContract.VaccineEntry.TABLE_NAME );
        }catch (SQLiteException e){
            e.printStackTrace();
        }
    }


    private void _InsertDefaultUser(SQLiteDatabase sqLiteDatabase) {
        User user = new User();
        user.setLastName("Test");
        user.setName("Test");
        user.setPassword("Test");
        user.setEmail("Test");
        user.setUserName("Test");
        long petTypeId = sqLiteDatabase.insert(
                UserContract.UserEntry.TABLE_NAME,
                null,
                user.toContentValues());
    }

    private void _InsertVaccinationPlans(SQLiteDatabase sqLiteDatabase) {

        VaccinationPlan itemPlan = new VaccinationPlan();
        //http://www.veterinariosbogotazoogar.com/cuidados-mascotas.html
        PetType petType = new PetType();
        petType.setName("Perros");
        petType.setDescription("Perros");
        long vaccineId =0;
        long petTypeId = sqLiteDatabase.insert(
        PetTypeContract.PetTypeEntry.TABLE_NAME,
        null,
        petType.toContentValues());
        ///Mascota de prueba
        Pet pet = new Pet();
        pet.setName("test");
        pet.setDescription("Test de test");
        pet.setPetTypeId((int)petTypeId);
        pet.setSex("M");
        pet.setBirthDate(new Date());
        sqLiteDatabase.insert(PetContract.PetEntry.TABLE_NAME, null,pet.toContentValues());
        {
            //Vacuna de moquillo canino
            {
                Vaccine itemVaccine = new Vaccine();
                itemVaccine.setName("Distemper");
                itemVaccine.setDescription("Moquillo canino");
                vaccineId = sqLiteDatabase.insert(
                        VaccineContract.VaccineEntry.TABLE_NAME,
                        null,
                        itemVaccine.toContentValues());

                {
                    itemPlan.setAgeInWeeks(7);
                    itemPlan.setPetTypeId((int) petTypeId);
                    itemPlan.setVaccineId((int) vaccineId);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    itemPlan.setAgeInWeeks(11);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    for (int i = 1; i <= 9; i++) {
                        itemPlan.setAgeInWeeks(11 + (52 * i));
                        sqLiteDatabase.insert(
                                VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                                null,
                                itemPlan.toContentValues());
                    }
                }
            }
            //Hepatitis infecciosa canina
            {
                Vaccine itemVaccine = new Vaccine();
                itemVaccine.setName("Hepatitis infecciosa canina");
                itemVaccine.setDescription("Hepatitis infecciosa canina");
                vaccineId = sqLiteDatabase.insert(
                        VaccineContract.VaccineEntry.TABLE_NAME,
                        null,
                        itemVaccine.toContentValues());

                {
                    itemPlan.setAgeInWeeks(7);
                    itemPlan.setPetTypeId((int) petTypeId);
                    itemPlan.setVaccineId((int) vaccineId);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    itemPlan.setAgeInWeeks(11);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    for (int i = 1; i <= 9; i++) {
                        itemPlan.setAgeInWeeks(11 + (52 * i));
                        sqLiteDatabase.insert(
                                VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                                null,
                                itemPlan.toContentValues());
                    }
                }
            }
            //Parvovirus canino
            {
                Vaccine itemVaccine = new Vaccine();
                itemVaccine.setName("Parvovirus canino");
                itemVaccine.setDescription("Parvovirus canino");
                vaccineId = sqLiteDatabase.insert(
                        VaccineContract.VaccineEntry.TABLE_NAME,
                        null,
                        itemVaccine.toContentValues());

                {
                    itemPlan.setAgeInWeeks(7);
                    itemPlan.setPetTypeId((int) petTypeId);
                    itemPlan.setVaccineId((int) vaccineId);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    itemPlan.setAgeInWeeks(11);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    for (int i = 1; i <= 9; i++) {
                        itemPlan.setAgeInWeeks(11 + (52 * i));
                        sqLiteDatabase.insert(
                                VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                                null,
                                itemPlan.toContentValues());
                    }
                }
            }
            //Leptospirosis
            {
                Vaccine itemVaccine = new Vaccine();
                itemVaccine.setName("Leptospirosis");
                itemVaccine.setDescription("Leptospirosis");
                vaccineId = sqLiteDatabase.insert(
                        VaccineContract.VaccineEntry.TABLE_NAME,
                        null,
                        itemVaccine.toContentValues());

                {
                    itemPlan.setAgeInWeeks(7);
                    itemPlan.setPetTypeId((int) petTypeId);
                    itemPlan.setVaccineId((int) vaccineId);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    itemPlan.setAgeInWeeks(11);
                    sqLiteDatabase.insert(
                            VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                            null,
                            itemPlan.toContentValues());
                    for (int i = 1; i <= 9; i++) {
                        itemPlan.setAgeInWeeks(11 + (52 * i));
                        sqLiteDatabase.insert(
                                VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                                null,
                                itemPlan.toContentValues());
                    }
                }
            }
            //Rabia
            {
                Vaccine itemVaccine = new Vaccine();
                itemVaccine.setName("Rabia");
                itemVaccine.setDescription("Rabia");
                vaccineId = sqLiteDatabase.insert(
                        VaccineContract.VaccineEntry.TABLE_NAME,
                        null,
                        itemVaccine.toContentValues());

                {
                    itemPlan.setAgeInWeeks(16);
                    itemPlan.setPetTypeId((int) petTypeId);
                    itemPlan.setVaccineId((int) vaccineId);
                    for (int i = 1; i <= 9; i++) {
                        itemPlan.setAgeInWeeks(16 + (52 * i));
                        sqLiteDatabase.insert(
                                VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME,
                                null,
                                itemPlan.toContentValues());
                    }

                }
            }
        }
    }

    private void _createVaccinationPlanPetsTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VaccinationPlanPetContract.VaccinationPlanPetEntry.TABLE_NAME + " ("
                + VaccinationPlanPetContract.VaccinationPlanPetEntry.VACCINATION_PLAN_PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VaccinationPlanPetContract.VaccinationPlanPetEntry.PET_ID + " INTEGER,"
                + VaccinationPlanPetContract.VaccinationPlanPetEntry.VACCINATION_PLAN_ID + " INTEGER,"
                + "UNIQUE (" + VaccinationPlanPetContract.VaccinationPlanPetEntry.VACCINATION_PLAN_PET_ID + "))");

    }
    private void _createVaccinationPlanTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VaccinationPlanContract.VaccinationPlanEntry.TABLE_NAME + " ("
                + VaccinationPlanContract.VaccinationPlanEntry.VACCINATION_PLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VaccinationPlanContract.VaccinationPlanEntry.PET_TYPE_ID + " INTEGER NOT NULL,"
                + VaccinationPlanContract.VaccinationPlanEntry.VACCINE_ID + " INTEGER NOT NULL,"
                + VaccinationPlanContract.VaccinationPlanEntry.AGE_IN_WEEKS + " INTEGER NOT NULL,"
                + "UNIQUE (" + VaccinationPlanContract.VaccinationPlanEntry.VACCINATION_PLAN_ID + "))");
    }

    private void _createPetTypeTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PetTypeContract.PetTypeEntry.TABLE_NAME + " ("
                + PetTypeContract.PetTypeEntry.PET_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PetTypeContract.PetTypeEntry.NAME + " TEXT NOT NULL,"
                + PetTypeContract.PetTypeEntry.DESCRIPTION + " TEXT NOT NULL,"
                + "UNIQUE (" + PetTypeContract.PetTypeEntry.PET_TYPE_ID  + "))");
    }

    private void _createUserTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                + UserContract.UserEntry.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + UserContract.UserEntry.FIRST_NAME + " TEXT NOT NULL,"
                + UserContract.UserEntry.LAST_NAME + " TEXT NOT NULL,"
                + UserContract.UserEntry.EMAIL  + " TEXT NOT NULL,"
                + UserContract.UserEntry.USER_NAME  +" TEXT NOT NULL,"
                + UserContract.UserEntry.PASSWORD + " TEXT NOT NULL,"
                + UserContract.UserEntry.PHOTO  + " BLOB NULL,"
                + "UNIQUE (" + UserContract.UserEntry.USER_ID  + "))");
    }
    private void _createPetTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + " ("
                + PetContract.PetEntry.PET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PetContract.PetEntry.USER_ID + " INTEGER NOT NULL,"
                + PetContract.PetEntry.PET_TYPE_ID + " INTEGER NOT NULL,"
                + PetContract.PetEntry.NAME + " TEXT NOT NULL,"
                + PetContract.PetEntry.BIRTH_DATE  + " TEXT NOT NULL,"
                + PetContract.PetEntry.DESCRIPTION  +" TEXT NOT NULL,"
                + PetContract.PetEntry.SEX + " TEXT NOT NULL,"
                + PetContract.PetEntry.PHOTO  + " BLOB NULL,"
                + "UNIQUE (" + PetContract.PetEntry.PET_ID  + "))");
    }
    private void _createVaccineTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + VaccineContract.VaccineEntry.TABLE_NAME + " ("
                + VaccineContract.VaccineEntry.VACCINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + VaccineContract.VaccineEntry.NAME + " TEXT NOT NULL,"
                + VaccineContract.VaccineEntry.DESCRIPTION + " TEXT NOT NULL,"
                + "UNIQUE (" + VaccineContract.VaccineEntry.VACCINE_ID  + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
