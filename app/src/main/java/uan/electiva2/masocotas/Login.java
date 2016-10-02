package uan.electiva2.masocotas;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uan.electiva2.masocotas.DataAccess.UserManager;
import uan.electiva2.masocotas.entities.Constants;
import uan.electiva2.masocotas.entities.User;

public class Login extends AppCompatActivity {

    /*Declaración de elementos de la actividad*/
    private EditText userText;
    private EditText passwordText;
    private Button loginButton;
    private Button registerButton;
    /*Fin de la declaración de elementos de la actividad*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userText = (EditText) findViewById(R.id.userText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!_validateAndLogin()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario o contraseña  no válidos", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Intent mainActivity = new Intent(Login.this, Main.class);
                mainActivity.putExtra(Constants.USER_NAME, userText.getText().toString());
                startActivity(mainActivity);
                return;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerActivity = new Intent(Login.this, Register.class);
                startActivity(registerActivity);
            }
        });
    }
    ///Método encargado de hacer las validaciones del formulario y enviar los datos al método de autenticación
    private boolean _validateAndLogin() {
        boolean isValid = true;
        String user = userText.getText().toString();
        String password = passwordText.getText().toString();
        // Reset errors.
        userText.setError(null);
        passwordText.setError(null);
        View focus;
        if (TextUtils.isEmpty(user)) {
            userText.setError("El usuario es obligatorio.");
            focus = userText;
            isValid = false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordText.setError("La contraseña es obligatoria.");
            focus = passwordText;
            isValid = false;
        }
        if (!isValid)
            return isValid;
        isValid=_login(user,password);
        return isValid;
    }
    ///Método encargado de hacer la autenticación
    private boolean _login(String userName, String password) {
        try {
            try (UserManager userManager = new UserManager(getApplicationContext())) {
                User user = userManager.getUser(userName, password);
                if (user == null)
                    return false;
                return true;
            }
        }catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

}