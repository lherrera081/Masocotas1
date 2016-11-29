package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import uan.electiva2.masocotas.Adapters.PetsCursorAdapter;
import uan.electiva2.masocotas.Adapters.VaccinesCursorAdapter;
import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.DataAccess.PetManager;
import uan.electiva2.masocotas.DataAccess.VaccinationPlanManager;
import uan.electiva2.masocotas.R;
import uan.electiva2.masocotas.entities.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VaccinesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VaccinesFragment extends Fragment {
    // TODO: Rename and change types of parameters
    private String petId;

    private ListView vaccinesList;
    private VaccinesCursorAdapter vaccinesAdapter;
    private VaccinationPlanManager vaccinationManager;

    public VaccinesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param petId .
     * @return A new instance of fragment VaccinesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VaccinesFragment newInstance(String petId) {
        VaccinesFragment fragment = new VaccinesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PET_ID, petId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vaccines, container, false);
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.MY_REFERENCES, Context.MODE_PRIVATE);
        petId = sharedpreferences.getString(Constants.PET_ID,"0");
        // Referencias UI
        vaccinesList = (ListView) root.findViewById(R.id.vaccines_list);
        vaccinesAdapter = new VaccinesCursorAdapter(getActivity(), null);
        // Setup
        vaccinesList.setAdapter(vaccinesAdapter);

        vaccinationManager = new VaccinationPlanManager(getActivity());
        // Carga de datos
        loadVaccines();
        //return inflater.inflate(R.layout.fragment_vaccines, container, false);
        return root;
    }

    private void loadVaccines() {
        new VaccinesLoadTask().execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petId = getArguments().getString(Constants.PET_ID);
        }
    }

    private class VaccinesLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return vaccinationManager.getAllVaccines();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                vaccinesAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
                showEmptyMessage();
            }
            //vaccinationManager.close();
        }

    }

    private void showEmptyMessage() {
        Toast.makeText(getActivity(),
                "No se encontraron vacunas para tu mascota", Toast.LENGTH_SHORT).show();
    }
}
