package RionaldiJmartFH.jmart_android;

import static RionaldiJmartFH.jmart_android.FilterFragment.productName;
import static RionaldiJmartFH.jmart_android.FilterFragment.products;
import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import RionaldiJmartFH.jmart_android.model.Account;
import RionaldiJmartFH.jmart_android.model.Payment;
import RionaldiJmartFH.jmart_android.model.Product;
import RionaldiJmartFH.jmart_android.model.ProductCategory;
import RionaldiJmartFH.jmart_android.request.PaymentCreateRequest;
/**
 *  Display the detail of the product
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class ProductDetailActivity extends AppCompatActivity {
    private static final Gson gson = new Gson();
    private static Payment paymentDetail = null;
    public static Payment getPayment(){
        return paymentDetail;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> pr = new ArrayList<>();
        setContentView(R.layout.activity_product_detail);
        Intent intent = getIntent();
        int position = intent.getIntExtra("Pos",0);
        System.out.println(position);
//        TextView PDidEdt  = findViewById(R.id.PDidEdt);
        TextView PDNameEdit  = findViewById(R.id.ProductNameView);
        TextView PDCategoryEdit  = findViewById(R.id.ProductCategoryView);
        TextView PDConditionEdit  = findViewById(R.id.ProductConditionView);
        TextView PDPriceEdit  = findViewById(R.id.ProductPriceView);
//        TextView PDDiscountEdit  = findViewById(R.id.PDDiscountEdit);
        TextView PDWeightEdit  = findViewById(R.id.ProductWeightView);
//        TextView PDShipmentEdit  = findViewById(R.id.PDShipmentEdit);
        EditText Address = findViewById(R.id.ShipAddress);
        EditText ProdCount = findViewById(R.id.ProductCount);


        Button CreatePayButton = findViewById(R.id.CreatePayButton);
        System.out.println(productName.get(position));
        for(Product prod: products){
            if(prod.name == productName.get(position)){
                pr.add(String.valueOf(prod.accountId));
                pr.add(prod.name);
                pr.add(String.valueOf(prod.category));
                pr.add(String.valueOf(prod.conditionUsed));
                pr.add(String.valueOf(prod.price));
                pr.add(String.valueOf(prod.discount));
                pr.add(String.valueOf(prod.weight));
                pr.add(String.valueOf(prod.shipmentPlans));
                pr.add(String.valueOf(prod.id));

            }
        }

//        PDidEdt.setText(pr.get(0));
        PDNameEdit.setText(pr.get(1));
        PDCategoryEdit.setText(pr.get(2));
        PDConditionEdit.setText(pr.get(3));
        PDPriceEdit.setText(pr.get(4));
//        PDDiscountEdit.setText(pr.get(5));
        PDWeightEdit.setText(pr.get(6));
//        PDShipmentEdit.setText(pr.get(7));

        CreatePayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject object = new JSONObject(response);
                            Intent intent = new Intent(ProductDetailActivity.this,ConfirmationActivity.class);
                            paymentDetail = gson.fromJson(object.toString(),Payment.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(ProductDetailActivity.this, "Error!", Toast.LENGTH_LONG);

                    }
                };
                PaymentCreateRequest paymentReq = new PaymentCreateRequest(String.valueOf(getLoggedAccount().id), String.valueOf(pr.get(8)),ProdCount.getText().toString(),
                        Address.getText().toString(),String.valueOf(pr.get(7)),listener,errorListener);
                RequestQueue requestQueue = Volley.newRequestQueue(ProductDetailActivity.this);
                requestQueue.add(paymentReq);
            }
        });

    }
}