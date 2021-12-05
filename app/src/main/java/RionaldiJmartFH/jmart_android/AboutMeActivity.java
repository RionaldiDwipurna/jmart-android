package RionaldiJmartFH.jmart_android;

import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import RionaldiJmartFH.jmart_android.request.CreateStoreRequest;

public class AboutMeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        TextView NameDyn = findViewById(R.id.NameDyn);
        TextView EmailDyn = findViewById(R.id.EmailDyn);
        TextView BalanceDyn = findViewById(R.id.BalanceDyn);
        Button Register = findViewById(R.id.ButtonRegister);

        CardView card2 = findViewById(R.id.card_view2);
        CardView card3 = findViewById(R.id.card_view3);


        NameDyn.setText(getLoggedAccount().name);
        EmailDyn.setText(getLoggedAccount().email);
        BalanceDyn.setText(String.valueOf(getLoggedAccount().balance));


        if(getLoggedAccount().store != null){
            card3.setVisibility(View.VISIBLE);
            TextView personName = findViewById(R.id.PersonName);
            TextView personAdd = findViewById(R.id.StoreAddress);
            TextView personPhone = findViewById(R.id.PersonPhone);
            personName.setText(getLoggedAccount().store.name);
            personPhone.setText(getLoggedAccount().store.phoneNumber);
            personAdd.setText(getLoggedAccount().store.address);

        } else{
            Register.setVisibility(View.VISIBLE);

        }
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("register");
                card2.setVisibility(View.VISIBLE);
                Button registerStore = findViewById(R.id.RegisterButton);
                registerStore.setVisibility(View.VISIBLE);
                EditText storeName = findViewById(R.id.RegisterName);
                EditText storeAddress = findViewById(R.id.RegisterAddress);
                EditText storePhoneNumber = findViewById(R.id.RegisterPhone);

                Button cancel = findViewById(R.id.CancelButton);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("Cancel");
                    }
                });
                registerStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((!storeName.getText().toString().isEmpty()) || (!storeAddress.getText().toString().isEmpty()) || (!storePhoneNumber.getText().toString().isEmpty())){
                            Response.Listener<String> listener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jObject = new JSONObject(response);
                                        if (jObject != null) {
                                            Toast.makeText(AboutMeActivity.this, "Register Success!", Toast.LENGTH_LONG).show();
                                            card2.setVisibility(View.GONE);
                                            card3.setVisibility(View.VISIBLE);
                                        } else {
                                            Toast.makeText(AboutMeActivity.this, "Register Failed!", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(AboutMeActivity.this, "Request Gagal!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            };
                            Response.ErrorListener errorListener = new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(AboutMeActivity.this, "Store Gagal Dibuat!", Toast.LENGTH_LONG);
                                }
                            };

                            CreateStoreRequest newStoreRequest = new CreateStoreRequest(getLoggedAccount().id,storeName.getText().toString(), storeAddress.getText().toString(), storePhoneNumber.getText().toString(), listener, errorListener);
                            RequestQueue queue = Volley.newRequestQueue(AboutMeActivity.this);
                            queue.add(newStoreRequest);
                        } else {
                            Toast.makeText(AboutMeActivity.this, "Please Fill in The Blanks!", Toast.LENGTH_LONG).show();
                        }



                    }
                });

            }
        });




    }
}