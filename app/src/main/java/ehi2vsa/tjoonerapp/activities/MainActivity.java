package ehi2vsa.tjoonerapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import ehi2vsa.tjoonerapp.R;
import ehi2vsa.tjoonerapp.async.LoginTask;
import ehi2vsa.tjoonerapp.singletons.LoginToken;

public class MainActivity extends AppCompatActivity {
    EditText usernameET, passwordET;
    LoginToken code = LoginToken.getInstance();
    Button button;
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
        usernameET = (EditText) findViewById(R.id.et_username);
        passwordET = (EditText) findViewById(R.id.et_password);
        cbUsername = (CheckBox) findViewById(R.id.CBRememberUserName);
        cbToken = (CheckBox) findViewById(R.id.CBRememberToken);
        intent = new Intent(this, LoggedIn.class);

        sharedPref = this.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);

        String savedToken = sharedPref.getString(ACCESS_TOKEN,null);
        if (sharedPref.getString(ACCESS_TOKEN,null) != null ) {
            Log.d("sharedPref", "onCreate: ACCES+_TOKEN IS"+savedToken);
            cbToken.setChecked(true);
            code.setCode(savedToken);
            startActivity(intent);
        } else{
            cbToken.setChecked(false);
        }
        String name = sharedPref.getString(USER,null);
        if (sharedPref.getString(USER,null)!=null)
        {
            cbUsername.setChecked(true);
            usernameET.setText(name);
        } else {
            cbUsername.setChecked(false);
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref = this.getSharedPreferences(PREFS_NAME, Context.MODE_MULTI_PROCESS);
        Log.d("sharedPref", "onCreate: ACCES+_TOKEN IS"+sharedPref.getString(ACCESS_TOKEN,null));
        if (sharedPref.getString(ACCESS_TOKEN,null) == null ) {
            cbToken.setChecked(false);

        }
        String name = sharedPref.getString(USER,null);
        if (sharedPref.getString(USER,null)!=null)
        {
            cbUsername.setChecked(true);
            usernameET.setText(name);
        } else {
            cbUsername.setChecked(false);
        }

    }
}