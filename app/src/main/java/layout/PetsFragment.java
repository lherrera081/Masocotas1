package layout;


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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import uan.electiva2.masocotas.Adapters.PetsCursorAdapter;
import uan.electiva2.masocotas.DataAccess.PetManager;
import uan.electiva2.masocotas.R;

public class PetsFragment extends Fragment {

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

        // Carga de datos
        loadPets();
        return root;
    }

    private void loadPets() {
        new PetsLoadTask().execute();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
}
