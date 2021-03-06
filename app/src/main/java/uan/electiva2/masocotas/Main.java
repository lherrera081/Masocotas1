package uan.electiva2.masocotas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import uan.electiva2.masocotas.DataAccess.UserManager;
import uan.electiva2.masocotas.entities.Constants;
import uan.electiva2.masocotas.entities.User;

public class Main extends AppCompatActivity {

    /*Declaración de elementos de la actividad*/
    ImageView photoImageButton;
    TextView fullNameTextView;
    Button loadPetsButton;
    /*Fin de la declaración de elementos de la actividad*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoImageButton = (ImageView) findViewById(R.id.photoImageButton);
        fullNameTextView = (TextView)findViewById(R.id.fullNameTextView);
        loadPetsButton = (Button)findViewById(R.id.loadPetsButton);
        Intent intent = getIntent();
        String userName = intent.getStringExtra(Constants.USER_NAME);
        User currentUser;
        try (UserManager userManager = new UserManager(getApplicationContext())) {
            currentUser = userManager.getUserByUsername(userName);
        }
        fullNameTextView.setText( currentUser.getName() + " "+ currentUser.getLastName());
        Bitmap currenUserPhoto = currentUser.getPhoto();
        if(currenUserPhoto !=null)
        {
            photoImageButton.setImageBitmap(currenUserPhoto);
        }
        loadPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(Main.this, Pets.class);
                mainActivity.putExtra(Constants.USER_NAME, getIntent().getStringExtra(Constants.USER_NAME));
                startActivity(mainActivity);
                return;
            }
        });

    }
}
