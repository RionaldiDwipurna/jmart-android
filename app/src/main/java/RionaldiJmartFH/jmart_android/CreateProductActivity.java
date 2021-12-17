package RionaldiJmartFH.jmart_android;

import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import RionaldiJmartFH.jmart_android.model.ProductCategory;
import RionaldiJmartFH.jmart_android.request.CreateProductRequest;
/**
 *  Activity to create the product
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class CreateProductActivity extends AppCompatActivity {

    private ProductCategory category;
    private boolean conditionUsed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product);
        getSupportActionBar().hide();

        EditText productName = findViewById(R.id.CPName);
        EditText productWeight = findViewById(R.id.CPWeight);
        EditText productPrice = findViewById(R.id.CPPrice);
        EditText productDiscount = findViewById(R.id.CPDiscount);
        RadioButton radioNew = findViewById(R.id.radioNew);
        RadioButton radioUsed = findViewById(R.id.radioUsed);
        Spinner productCategory = findViewById(R.id.categorySpinner);
        Spinner productShipmentPlans = findViewById(R.id.shipmentSpinner);

        Button productCreate = findViewById(R.id.SubmitButton);
        Button cancelB = findViewById(R.id.CancelButt);
        productCategory.setAdapter(new ArrayAdapter<ProductCategory>(this, android.R.layout.simple_spinner_dropdown_item, ProductCategory.values()));

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                System.out.println(productShipmentPlans.getSelectedItem().toString());
            }
        });
        productCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionUsed = !radioNew.isChecked();

                byte validShipmentPlan = checkShipmentPlan(productShipmentPlans);
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jObject = new JSONObject(response);
                            if (jObject != null) {
                                Toast.makeText(CreateProductActivity.this, "Produk Berhasil Dibuat!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(CreateProductActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(CreateProductActivity.this, "Produk Gagal Dibuat!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CreateProductActivity.this, "Produk Gagal Dibuat!", Toast.LENGTH_LONG).show();
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CreateProductActivity.this, "Produk Gagal Dibuat!", Toast.LENGTH_LONG).show();
                    }
                };
                CreateProductRequest createRequest = new CreateProductRequest(
                        String.valueOf(getLoggedAccount().id),
                        productName.getText().toString(),
                        productWeight.getText().toString(),
                        String.valueOf(conditionUsed),
                        productPrice.getText().toString(),
                        productDiscount.getText().toString(),
                        productCategory.getSelectedItem().toString(),
                        String.valueOf(validShipmentPlan),
                        listener, errorListener);
                RequestQueue queue = Volley.newRequestQueue(CreateProductActivity.this);
                queue.add(createRequest);
            }
        });
    }

    private byte checkShipmentPlan(Spinner productShipmentPlan) {
        byte bit = 1;
        if (productShipmentPlan.getSelectedItem().toString().equals("INSTANT")) {
            bit = (byte)1;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("SAME DAY")) {
            bit = (byte)2;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("NEXT DAY")) {
            bit = (byte)4;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("REGULER")) {
            bit = (byte)8;
        } else if (productShipmentPlan.getSelectedItem().toString().equals("KARGO")) {
            bit = (byte)16;
        }
        return bit;
    }
}