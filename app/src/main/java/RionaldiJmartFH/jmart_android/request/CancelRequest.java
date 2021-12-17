package RionaldiJmartFH.jmart_android.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  Request to cancel the payment
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class CancelRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/payment/%d/cancel";
    private static Map<String, String> params = new HashMap<>();
    public CancelRequest(int id, Response.Listener<String> listener,
                         Response.ErrorListener errorListener){
        super(Request.Method.POST, String.format(URL, id), listener, errorListener);
    }
    public Map<String, String> getParams() { return params; }

}
