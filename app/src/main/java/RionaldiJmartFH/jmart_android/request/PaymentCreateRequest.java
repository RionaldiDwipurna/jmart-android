package RionaldiJmartFH.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentCreateRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/payment/create";
    private final Map<String,String> params;

    public PaymentCreateRequest(String buyerId, String productId, String productCount, String shipmentAddress, String shipmentPlan,
                                Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Request.Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("buyerId",buyerId);
        params.put("productId",productId);
        params.put("productCount",productCount);
        params.put("shipmentAddress",shipmentAddress);
        params.put("shipmentPlan",shipmentPlan);


    }
    @Override
    public Map<String,String> getParams(){return this.params;}
}
