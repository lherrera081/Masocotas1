package layout;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import uan.electiva2.masocotas.AddEditPetActivity;
import uan.electiva2.masocotas.Contracts.PetContract;
import uan.electiva2.masocotas.DataAccess.PetManager;
import uan.electiva2.masocotas.R;
import uan.electiva2.masocotas.entities.Constants;
import uan.electiva2.masocotas.entities.Pet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PetDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetDetailFragment extends Fragment {

    private String petId;
    private TextView petName;
    private TextView petBirthDate;
    private TextView petDescription;
    private TextView petSex;
    private TextView petType;
    private PetManager petManager;

    public PetDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @petId petId.
     * @return A new instance of fragment PetDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetDetailFragment newInstance(String petId) {
        PetDetailFragment fragment = new PetDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PET_ID, petId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.petId = getArguments().getString(Constants.PET_ID);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pet_detail, container, false);
        petName = (TextView) root.findViewById(R.id.petNameTextView);
        petBirthDate = (TextView) root.findViewById(R.id.petBirthDayTextView);
        petDescription = (TextView) root.findViewById(R.id.petDescriptionTextView);
        petSex = (TextView) root.findViewById(R.id.petSexTextView);
        petType = (TextView) root.findViewById(R.id.petTypeTextView);
        petManager = new PetManager(getActivity());
        loadPet();
        petManager.close();
        // Inflate the layout for this fragment
        return root;
    }

    private void loadPet() {
        Pet pet = petManager.getPetById(petId);
        petName.setText( pet.getName());
        petDescription.setText(pet.getDescription());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        petBirthDate.setText(dateFormat.format(pet.getBirthDate()));
        petSex.setText(pet.getSex());
        if(pet.getPetType()!=null)
            petType.setText(pet.getPetType().getName());
        petManager.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                //new DeleteLawyerTask().execute();
                int i= petManager.deletePet(petId);
                showPetsScreen(i > 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditPetActivity.class);
        intent.putExtra(Constants.PET_ID, petId);
        startActivityForResult(intent, PetsFragment.REQUEST_UPDATE_DELETE_PET);

    }

    private void showPetsScreen(boolean deleteResult) {
        if (!deleteResult) {
            showDeleteError();
        }
        getActivity().setResult(deleteResult ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }
    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar mascota", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case PetsFragment.REQUEST_UPDATE_DELETE_PET:
                    loadPet();
                    getActivity().setResult(Activity.RESULT_OK);
                    break;
            }
        }
    }
}
