package layout;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import uan.electiva2.masocotas.DataAccess.PetManager;
import uan.electiva2.masocotas.R;
import uan.electiva2.masocotas.entities.Constants;
import uan.electiva2.masocotas.entities.Pet;
import uan.electiva2.masocotas.entities.PetType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditPetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditPetFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String petId;
    private FloatingActionButton saveButton;
    private PetManager petManager;
    private EditText petName;
    private DatePicker petBirthDate;
    private EditText petDescription;
    private Spinner petSex;
    private Spinner petType;
    private PetType[] petTypes;
    private String[] sexList;

    public AddEditPetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param petId Parameter 1.
     * @return A new instance of fragment AdEditPetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditPetFragment newInstance(String petId) {
        AddEditPetFragment fragment = new AddEditPetFragment();
        Bundle args = new Bundle();
        args.putString(Constants.PET_ID, petId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petId = getArguments().getString(Constants.PET_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_pet, container, false);
        saveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        petName = (EditText) root.findViewById(R.id.textPetName);
        petDescription = (EditText) root.findViewById(R.id.textDescription);
        petType = (Spinner)root.findViewById(R.id.spinnerPetType);
        petManager = new PetManager(getActivity());
        petSex  = (Spinner)root.findViewById(R.id.spinnerSex);
        petBirthDate = (DatePicker) root.findViewById(R.id.petBirthDate);

        petTypes = petManager.getPetTypes();
        ArrayAdapter<PetType> adapter = new ArrayAdapter<PetType>(getActivity(), android.R.layout.simple_spinner_dropdown_item, petTypes);
        petType.setAdapter(adapter);
        sexList = new String[]{ "Masculino", "Femenino" };
        ArrayAdapter<String> sexAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sexList);
        petSex.setAdapter(sexAdapter);

        // Eventos
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditPet();
            }
        });
        // Carga de datos
        if (petId != null) {
            loadPet();
        }
        return root;
    }

    private void loadPet() {
        if(petId==null)
            return;
        Pet pet = petManager.getPetById(petId);
        petName.setText(pet.getName());
        petDescription.setText(pet.getDescription());
        petType.setSelection(getIndexPetType(pet.getPetTypeId()));
        petSex.setSelection(getIndexSex(pet.getSex()));
        petBirthDate.updateDate(pet.getBirthDate().getYear(),pet.getBirthDate().getMonth(),pet.getBirthDate().getDay());
        petManager.close();
    }

    private int getIndexPetType(int petTypeId){
        int index = 0;
        for (int i=0;i<petType.getCount();i++){
            if (((PetType)petType.getItemAtPosition(i)).getPetTypeId()== petTypeId){
                index = i;
            }
        }
        return index;
    }
    private int getIndexSex(String sex){
        int index = 0;
        for (int i=0;i<petSex.getCount();i++){
            if (petSex.getItemAtPosition(i).toString().equals(sex)){
                index = i;
            }
        }
        return index;
    }
    private void addEditPet() {
        Pet pet = new Pet();
        if(petId!=null) {
            pet.setPetId(Integer.parseInt(petId));
        }
        pet.setName(petName.getText().toString());
        pet.setDescription(petDescription.getText().toString());
        pet.setBirthDate(new Date(petBirthDate.getYear(), petBirthDate.getMonth()+1,petBirthDate.getDayOfMonth()));
        PetType selectedPetType=(PetType)petType.getSelectedItem();
        if(selectedPetType !=null)
            pet.setPetTypeId(selectedPetType.getPetTypeId());
        String selectedSex = petSex.getSelectedItem().toString();
        if(selectedPetType !=null)
            pet.setSex(selectedSex);
        Boolean error = false;
        if (TextUtils.isEmpty(pet.getName())) {
            petName.setError("El nombre es obligatoria");
            error = true;
        }
        if (TextUtils.isEmpty(pet.getDescription())) {
            petName.setError("La descripción es obligatoria");
            error = true;
        }
        if (pet.getPetTypeId() == 0) {
            petName.setError("El tipo de mascota es obligatorio");
            error = true;
        }
        if (TextUtils.isEmpty(pet.getSex())) {
            petName.setError("El sexo de la mascota es obligatoria");
            error = true;
        }
        if (pet.getBirthDate() == null) {
            petName.setError("La fecha de nacimiento es obligatoria");
            error = true;
        }

        if (error) {
            return;
        }
        ///Guarda y envía al listado de mascotas
        int result;
        if(petId==null) {
            result = petManager.InsertPet(pet);
        }else {
            result = petManager.UpdatePet(pet);
        }
        showPetsScreen(result>0);
    }
    private void showPetsScreen(boolean editResult) {
        if (!editResult) {
            showDeleteError();
            return;
        }
        petManager.close();
        getActivity().setResult(editResult ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al guardar la información de la mascota", Toast.LENGTH_SHORT).show();
    }

}
