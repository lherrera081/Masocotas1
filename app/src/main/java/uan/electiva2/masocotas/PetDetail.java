package uan.electiva2.masocotas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import layout.PetDetailFragment;
import layout.PetsFragment;
import layout.VaccinesFragment;
import uan.electiva2.masocotas.entities.Constants;

public class PetDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra(Constants.PET_ID);
        PetDetailFragment fragment = (PetDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.pet_detail_container);
        if (fragment == null) {
            fragment = PetDetailFragment.newInstance(id);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.pet_detail_container, fragment)
                    .commit();
        }

        VaccinesFragment vaccinesFragment = (VaccinesFragment)
                getSupportFragmentManager().findFragmentById(R.id.vaccines_container);

        if (vaccinesFragment == null) {
            vaccinesFragment = new VaccinesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.vaccines_container, vaccinesFragment)
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pet_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
