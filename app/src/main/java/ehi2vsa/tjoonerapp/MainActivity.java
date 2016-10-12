package ehi2vsa.tjoonerapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ehi2vsa.tjoonerapp.async.LoginTask;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

public class MainActivity extends AppCompatActivity {
    EditText usernameET, passwordET;
    LoginToken code = LoginToken.getInstance();
    Button button, loginhack;
    Intent intent;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        loginhack = (Button) findViewById(R.id.login_hack);
        usernameET = (EditText) findViewById(R.id.et_username);
        passwordET = (EditText) findViewById(R.id.et_password);
        intent = new Intent(this, LoggedIn.class);

        loginhack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = "s2";
                String password = "s1a2x3i4o5n6";
                LoginTask task = new LoginTask(username, password);
                task.execute();
                try {
                    String token = task.get();
                    System.out.println(token);
                    if (!token.contains("Username") && !token.contains("000000000000") && !token.equals("Error")) {
                        code.setCode(token);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();

                LoginTask task = new LoginTask(username, password);
                task.execute();
                try {
                    String token = task.get();
                    System.out.println(token);
                    if (!token.contains("Username") && !token.contains("000000000000") && !token.equals("Error")) {
                        code.setCode(token);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
    }
}