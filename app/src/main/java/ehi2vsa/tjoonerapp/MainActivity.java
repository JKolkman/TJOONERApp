package ehi2vsa.tjoonerapp;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ehi2vsa.tjoonerapp.singletons.LoginCode;

public class MainActivity extends AppCompatActivity {
    String loginToken;
    URL url;
    HttpURLConnection urlConnection;
    EditText usernameET, passwordET;
    LoginCode code = LoginCode.getInstance();
    Button button;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        usernameET = (EditText) findViewById(R.id.et_username);
        passwordET = (EditText) findViewById(R.id.et_password);
        intent = new Intent(this, LoggedIn.class);


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
                    if (!token.contains("Username") && !token.contains("000000000000")) {
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

    private class LoginTask extends AsyncTask<String, String, String> {
        String username;
        String password;

        public LoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                url = new URL("http://setup.tjooner.tv/JCC/Saxion/TJOONER/REST/api/login");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");


                urlConnection.setRequestProperty("Content-type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                String req = "{\n \"Username\": \"" + username + "\", \n \"Password\": \"" + password + "\"\n";
                urlConnection.setRequestProperty("Content-length", req.getBytes().length + "");
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);
                urlConnection.connect();

                OutputStream out = urlConnection.getOutputStream();
                out.write(req.getBytes("UTF-8"));
                out.flush();
                out.close();

                int statusCode = urlConnection.getResponseCode();

                Log.d("failed", "doInBackground(): connection failed: statusCode: " + statusCode);
                String response = urlConnection.getResponseMessage();
                Log.d("ResponseConnection", response);

                InputStream in = new BufferedInputStream(
                        urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result.toString());
                return result.toString();
            } catch (MalformedURLException a) {
                System.out.println(a.getMessage());
            } catch (IOException b) {
                System.out.println(b.getMessage());
            }
            return null;
        }
    }
}
