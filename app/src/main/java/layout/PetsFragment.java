package layout;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import uan.electiva2.masocotas.Adapters.PetsCursorAdapter;
import uan.electiva2.masocotas.AddEditPetActivity;
import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.DataAccess.PetManager;
import uan.electiva2.masocotas.PetDetail;
import uan.electiva2.masocotas.R;
import uan.electiva2.masocotas.entities.Constants;

public class PetsFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_PET = 100;
    public static final int REQUEST_ADD_PET = 101;
    private String mLawyerId;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mPhoneNumber;
    private TextView mSpecialty;
    private TextView mBio;

    private ListView petsList;
    private PetsCursorAdapter petsAdapter;
    private FloatingActionButton mAddButton;

    private PetManager petsManager;


    public PetsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pets, container, false);
        // Referencias UI
        petsList = (ListView) root.findViewById(R.id.pets_list);
        petsAdapter = new PetsCursorAdapter(getActivity(), null);
        mAddButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        // Setup
        petsList.setAdapter(petsAdapter);

        petsManager= new PetManager(getActivity());
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        // Carga de datos
        loadPets();
        //Evemto del click
        petsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) petsAdapter.getItem(i);
                String currentLawyerId = currentItem.getString(
                        currentItem.getColumnIndex(PetContract.PetEntry._ID));
                showDetailScreen(currentLawyerId);
            }
        });
        return root;
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditPetActivity.class);
        startActivityForResult(intent, REQUEST_ADD_PET);
    }

    private void showDetailScreen(String petId) {
        Intent intent = new Intent(getActivity(), PetDetail.class);
        intent.putExtra(Constants.PET_ID, petId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_PET);
    }

    private void loadPets() {
        new PetsLoadTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_UPDATE_DELETE_PET:
                    loadPets();
                    break;
                case REQUEST_ADD_PET:
                    showSuccessfullSavedMessage();
                    loadPets();
                    break;
            }
        }
    }
    private class PetsLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return petsManager.getAllPets();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                petsAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state

            }
            petsManager.close();
        }

    }
    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Mascota guardada correctamente", Toast.LENGTH_SHORT).show();
    }
}
