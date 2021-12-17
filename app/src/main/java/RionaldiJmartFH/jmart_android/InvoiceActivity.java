package RionaldiJmartFH.jmart_android;

import static RionaldiJmartFH.jmart_android.FilterFragment.productName;
import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

import RionaldiJmartFH.jmart_android.model.Payment;
import RionaldiJmartFH.jmart_android.model.Product;
import RionaldiJmartFH.jmart_android.request.RequestFactory;

public class InvoiceActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    public static final ArrayList<Integer> invoiceId = new ArrayList<>();
    public static ArrayList<Payment> invoice = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    invoice.clear();
                    invoiceId.clear();
                    JSONArray jArray = new JSONArray(response);
                    Type type = new TypeToken<ArrayList<Payment>>(){}.getType();
                    invoice = gson.fromJson(String.valueOf(jArray), type);
                    System.out.println(jArray);
                    System.out.println(invoice);
                    for (Payment pay : invoice) {
                        invoiceId.add((pay.id));
                    }
                    System.out.println(invoiceId);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        try {
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(InvoiceActivity.this, "Error Terjadi!", Toast.LENGTH_LONG).show();
                }
            };
            StringRequest request = RequestFactory.getPayment(getLoggedAccount().id,listener,errorListener);
            RequestQueue queue = Volley.newRequestQueue(InvoiceActivity.this);
            queue.add(request);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(InvoiceActivity.this, "Error Terjadi!", Toast.LENGTH_LONG).show();
        }

        //Thread agar menunggu data dapat di looping terlebih dahulu
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                ListView listView = (ListView) findViewById(R.id.ListPayment);

                ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(InvoiceActivity.this, android.R.layout.simple_list_item_1, invoiceId);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(InvoiceActivity.this, InvoiceDetail.class);
                        intent.putExtra("Position",position);
                        startActivity(intent);


                    }


                });
            }
        }, 2000);




    }
}