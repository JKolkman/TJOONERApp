package ehi2vsa.tjoonerapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    CheckBox cbUsername, cbToken;
    SharedPreferences sharedPref;
    String ACCESS_TOKEN = "ACCESS_TOKEN";
    String USER = "USER";
    final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.Login_button);
//        loginhack = (Button) findViewById(R.id.login_hack);
        usernameET = (EditText) findViewById(R.id.et_username);
        passwordET = (EditText) findViewById(R.id.et_password);
        cbUsername = (CheckBox) findViewById(R.id.CBRememberUserName);
        cbToken = (CheckBox) findViewById(R.id.CBRememberToken);
        intent = new Intent(this, LoggedIn.class);

        sharedPref = this.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
       //
        String savedToken = sharedPref.getString(ACCESS_TOKEN,null);
        if (sharedPref.getString(ACCESS_TOKEN,null) != null ) {
            cbToken.setChecked(true);
            code.setCode(savedToken);
            startActivity(intent);
        }
        String name = sharedPref.getString(USER,null);
        if (sharedPref.getString(USER,null)!=null)
        {
            cbUsername.setChecked(true);
            usernameET.setText(name);
        }
//
        //            loginhack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String username = "s2";
//                    String password = "s1a2x3i4o5n6";
//                    LoginTask task = new LoginTask(username, password);
//                    task.execute();
//                    try {
//                        String token = task.get();
//                        System.out.println(token);
//                        if (!token.contains("Username") && !token.contains("000000000000") && !token.equals("Error")) {
//                            code.setCode(token);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    }
//                }
//            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("onClick", "onClick: clicked on button");
                    String username = usernameET.getText().toString();
                    String password = passwordET.getText().toString();

                    if (cbUsername.isChecked()) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.putString(USER, username);
                        editor.apply();
                        Log.d("put", "onClick: putString"+sharedPref.getString(USER,null)+" with value of username"+username);
                    } else {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.clear();
                        editor.remove(USER);
                        editor.apply();
                    }

                    LoginTask task = new LoginTask(username, password);
                    task.execute();
                    try {
                        String token = task.get();
                        System.out.println(token);
                        if (!token.contains("Username") && !token.contains("000000000000") && !token.equals("Error")) {
                            code.setCode(token);
                            if (cbToken.isChecked()) {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.putString(ACCESS_TOKEN, token);
                                editor.apply();
                                Log.d("put", "onClick: putString"+sharedPref.getString(ACCESS_TOKEN,null)+" with value of token "+token);
                            } else {
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.remove(ACCESS_TOKEN);
                                editor.apply();
                            }
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
//}