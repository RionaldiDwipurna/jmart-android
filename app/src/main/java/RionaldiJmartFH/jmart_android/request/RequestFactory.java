package RionaldiJmartFH.jmart_android.request;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  Request to get a spesific method
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class RequestFactory
{
    private static final String URL_FORMAT_ID = "http://10.0.2.2:6969/%s/%d";
    private static final String URL_FORMAT_PAGE = "http://10.0.2.2:6969/%s/page?page=%s&pageSize=%s";
    private static final String URL_FORMAT_PRODUCT = "http://10.0.2.2:6969/product/getFiltered?page=%s&pageSize=%s&accountId=%d&search=%s&minPrice=%s&maxPrice=%s&category=%s&conditionUsed=%s";
    private static final String URL_PAYMENT = "http://10.0.2.2:6969/payment/%d/getPayment";

    /**
     *  Request to get an id
     */
    public static StringRequest getById
            (
                    String parentURI,
                    int id,
                    Response.Listener<String> listener,
                    Response.ErrorListener errorListener
            )
    {
        String url = String.format(URL_FORMAT_ID, parentURI, id);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     *  Request to get the current page
     */
    public static StringRequest getPage
            (
                    String parentURI,
                    int page,
                    int pageSize,
                    Response.Listener<String> listener,
                    Response.ErrorListener errorListener
            )
    {
        String url = String.format(URL_FORMAT_PAGE, parentURI, page, pageSize);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);
    }

    /**
     *  Request to get products after the product get filtered
     */
    public static StringRequest getProduct(
        int page,
        int pageSize,
        int id,
        String search,
        String minPrice,
        String maxPrice,
        String category,
        String conditionUsed,
        Response.Listener<String> listener,
        Response.ErrorListener errorListener
    )
    {
        String url = String.format(URL_FORMAT_PRODUCT,page,pageSize,id,search,minPrice,maxPrice,category,conditionUsed);
        return new StringRequest(Request.Method.GET, url, listener, errorListener);

    }

    /**
     *  Request to get payment detail
     */
    public static StringRequest getPayment(
            int id,
            Response.Listener<String> listener,
            Response.ErrorListener errorListener
    ){
        String url = String.format(URL_PAYMENT,id);
        return new StringRequest(Request.Method.GET,url,listener,errorListener);
    }

}