package uan.electiva2.masocotas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import layout.AddEditPetFragment;
import uan.electiva2.masocotas.entities.Constants;

public class AddEditPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String petId = getIntent().getStringExtra(Constants.PET_ID);
        setTitle(petId == null ? "Añadir mascota" : "Editar Mascota");
        AddEditPetFragment addEditLawyerFragment = (AddEditPetFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_pet_container);
        if (addEditLawyerFragment == null) {
            addEditLawyerFragment = AddEditPetFragment.newInstance(petId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_pet_container, addEditLawyerFragment)
                    .commit();
        }

    }

}
