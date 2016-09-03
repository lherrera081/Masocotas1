package uan.electiva2.masocotas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import uan.electiva2.masocotas.entities.Constants;
import uan.electiva2.masocotas.entities.User;

public class Main extends AppCompatActivity {

    /*Declaración de elementos de la actividad*/
    ImageButton photoImageButton;
    TextView fullNameTextView;
    /*Fin de la declaración de elementos de la actividad*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoImageButton = (ImageButton) findViewById(R.id.photoImageButton);
        fullNameTextView = (TextView)findViewById(R.id.fullNameTextView);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(Constants.USER_NAME);
        User currentUser = Data.getDataSingleton().getUserByUsername(userName);
        fullNameTextView.setText( currentUser.getName() + " "+ currentUser.getLastName());
        Bitmap currenUserPhoto = currentUser.getPhoto();
        if(currenUserPhoto !=null)
        {
            photoImageButton.setImageBitmap(currenUserPhoto);
        }
    }
}
