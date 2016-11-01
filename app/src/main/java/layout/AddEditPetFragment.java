package layout;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
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
    private static final int MY_REQUEST_CODE = 13246;
    private static final int PIC_CROP = 2;
    private static final int CAMERA_CAPTURE = 1;
    // TODO: Rename and change types of parameters
    private String userId;
    private String petId;
    private FloatingActionButton saveButton;
    private PetManager petManager;
    private EditText petName;
    private ImageView imageViewMascota;
    private DatePicker petBirthDate;
    private EditText petDescription;
    private Spinner petSex;
    private Spinner petType;
    private PetType[] petTypes;
    private String[] sexList;
    private Bitmap photoBitmap;
    private Intent cameraActivity;
    private FloatingActionButton editImagePet;
    private String mCurrentPhotoPath;
    private Uri picUri;

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

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(Constants.MY_REFERENCES, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString(Constants.USER_ID,"0");
        saveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        petName = (EditText) root.findViewById(R.id.textPetName);
        petDescription = (EditText) root.findViewById(R.id.textDescription);
        petType = (Spinner)root.findViewById(R.id.spinnerPetType);
        petManager = new PetManager(getActivity());
        petSex  = (Spinner)root.findViewById(R.id.spinnerSex);
        petBirthDate = (DatePicker) root.findViewById(R.id.petBirthDate);
        imageViewMascota = (ImageView) root.findViewById(R.id.imageViewMascota);
        editImagePet = (FloatingActionButton) root.findViewById(R.id.editImage);

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
        imageViewMascota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_REQUEST_CODE);
                }
                else if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_REQUEST_CODE);
                }else{
                    _openCamera();
                }
            }
        });
        editImagePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               performCrop();
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
        petBirthDate.updateDate(pet.getBirthDate().getYear()+1900,pet.getBirthDate().getMonth(),pet.getBirthDate().getDay());
        photoBitmap = pet.getPhoto();
        if(photoBitmap !=null){
            imageViewMascota.setImageBitmap(photoBitmap);
            imageViewMascota.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
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
        pet.setUserId(Integer.valueOf(userId));
        pet.setName(petName.getText().toString());
        pet.setDescription(petDescription.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString=String.valueOf(petBirthDate.getYear())+"-"+String.valueOf(petBirthDate.getMonth())+"-"+String.valueOf(petBirthDate.getDayOfMonth());
        try {
            pet.setBirthDate(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
        pet.setPhoto(photoBitmap);
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

    private void _openCamera() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        cameraActivity = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraActivity.resolveActivity(getActivity().getPackageManager()) != null) {
            // Continue only if the File was successfully created
            if (photoFile != null) {
                picUri =Uri.fromFile(photoFile);
                cameraActivity.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(cameraActivity, CAMERA_CAPTURE);
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_CAPTURE){
                performCrop();
            }else if(requestCode == PIC_CROP){
                try {
                    photoBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),picUri);
                    imageViewMascota.setImageBitmap(photoBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void performCrop(){
        if(picUri == null)
        {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No se ha tomado ninguna foto.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

}
