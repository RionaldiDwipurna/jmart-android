package RionaldiJmartFH.jmart_android.request;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;
/**
 *  Request logged the account
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class LoginRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:6969/account/login";
    private final Map<String,String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(Method.POST, URL, listener, errorListener);
        params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
    }

    public Map<String,String> getParams(){return params;}
}
