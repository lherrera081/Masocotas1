package uan.electiva2.masocotas;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import uan.electiva2.masocotas.DataAccess.UserManager;
import uan.electiva2.masocotas.entities.LogicException;
import uan.electiva2.masocotas.entities.User;

public class Register extends AppCompatActivity {

    private static final int MY_REQUEST_CODE = 13245;
    /*Declaración de elementos de la actividad*/
    private EditText nameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText userText;
    private EditText passwordText;
    ImageButton photoImageButton;
    Button registerButton;
    Intent cameraActivity;
    Bitmap photoBitmap;
    /*Fin de la declaración de elementos de la actividad*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameText = (EditText) findViewById(R.id.nameText);
        lastNameText = (EditText) findViewById(R.id.lastNameText);
        emailText = (EditText) findViewById(R.id.emailText);
        userText = (EditText) findViewById(R.id.userText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        photoImageButton = (ImageButton) findViewById(R.id.photoImageButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        photoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_REQUEST_CODE);
                }else{
                    _openCamera();
                }
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userText.setError(null);
                User entity = _mapToEntityAndValidate();
                if(entity == null)
                    return;
                try {
                    UserManager userManager = new UserManager(getApplicationContext());
                    userManager.insertUser(entity);
                    userManager.close();
                }catch(LogicException ex){
                    userText.setError(ex.getMessage());;
                }
                Intent mainActivity = new Intent(Register.this, Login.class);
                startActivity(mainActivity);
            }
        });
    }

    private User _mapToEntityAndValidate() {
        User entity = new User();
        entity.setName(nameText.getText().toString());
        entity.setLastName(lastNameText.getText().toString());
        entity.setEmail(emailText.getText().toString());
        entity.setUserName(userText.getText().toString());
        entity.setPassword(passwordText.getText().toString());
        entity.setPhoto(photoBitmap);
        if(_validateEntity(entity)== false)
            return null;
        return entity;
    }

    private boolean _validateEntity(User entity) {
        boolean isValid=true;
        View focus;
        if (TextUtils.isEmpty(entity.getName())) {
            nameText.setError("El nombre es obligatorio.");
            focus = userText;
            isValid = false;
        }
        if (TextUtils.isEmpty(entity.getLastName())) {
            lastNameText.setError("El apellido es obligatorio.");
            focus = userText;
            isValid = false;
        }
        if (TextUtils.isEmpty(entity.getEmail())) {
            emailText.setError("El correo electrónico es obligatorio.");
            focus = userText;
            isValid = false;
        }else if(entity.getEmail().contains("@") == false){
            emailText.setError("El correo electrónico es obligatorio.");
            focus = userText;
            isValid = false;
        }
        if (TextUtils.isEmpty(entity.getUserName())) {
            userText.setError("El usuario es obligatorio.");
            focus = userText;
            isValid = false;
        }
        if (TextUtils.isEmpty(entity.getPassword())) {
            passwordText.setError("La contraseña es obligatoria.");
            focus = userText;
            isValid = false;
        }
        return isValid;
    }

    private void _openCamera() {
        cameraActivity  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraActivity,0);
    }

    //Razón de tamaño h = h1*w/w1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            Bundle extra = data.getExtras();
            photoBitmap = (Bitmap) extra.get("data");
            //TODO: Cambiar de tamaño a la imagen.
//            int width=photoImageButton.getWidth();
//            int height= photoBitmap.getHeight()*width/photoBitmap.getWidth();
//            photoBitmap = Bitmap.createScaledBitmap(photoBitmap, width, height, true);
            photoImageButton.setImageBitmap(photoBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                _openCamera();
            }
            else {

            }
        }
    }

}
