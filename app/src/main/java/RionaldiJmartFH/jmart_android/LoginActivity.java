package RionaldiJmartFH.jmart_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.AccessControlContext;

import RionaldiJmartFH.jmart_android.model.Account;
import RionaldiJmartFH.jmart_android.request.LoginRequest;

/**
 *  Activity to logged the user in
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */

public class LoginActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Account loggedAccount = null;

    /**
     *  To return the logged account
     */
    public static Account getLoggedAccount(){
        return loggedAccount;
    }

    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String savedEmail, savedPassword;

    /**
     *  To automaticly start the account if the user hasn't logged out
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (savedEmail != null && savedPassword != null) {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject object = new JSONObject(response);
                        if(object != null){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            loggedAccount = gson.fromJson(object.toString(), Account.class);
                            startActivity(intent);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            LoginRequest loginRequest = new LoginRequest(savedEmail.toString(), savedPassword.toString(),listener,null);
            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(loginRequest);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        Button button = findViewById(R.id.cirLoginButton);
        TextView register = findViewById(R.id.Register2);


        // getting the data which is stored in shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // in shared prefs inside het string method
        // we are passing key value as EMAIL_KEY and
        // default value is
        // set to null if not present.
        savedEmail = sharedpreferences.getString(EMAIL_KEY, null);
        savedPassword = sharedpreferences.getString(PASSWORD_KEY, null);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject object = new JSONObject(response);
                            if(object != null){
                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                // below two lines will put values for
                                // email and password in shared preferences.
                                editor.putString(EMAIL_KEY, email.getText().toString());
                                editor.putString(PASSWORD_KEY, password.getText().toString());

                                // to save our data with key and value.
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                loggedAccount = gson.fromJson(object.toString(), Account.class);
                                startActivity(intent);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_LONG);

                    }
                };
                LoginRequest loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString(),listener,null);
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(loginRequest);
            }
        });
    }
}