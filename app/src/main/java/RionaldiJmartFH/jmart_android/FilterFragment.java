package RionaldiJmartFH.jmart_android;

import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import RionaldiJmartFH.jmart_android.model.Product;
import RionaldiJmartFH.jmart_android.model.ProductCategory;
import RionaldiJmartFH.jmart_android.request.RequestFactory;

/**
 *  Activity to filter the product
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class FilterFragment extends Fragment {
    private static final Gson gson = new Gson();
    public static final ArrayList<String> productName = new ArrayList<>(new HashSet<>());
    public static ArrayList<Product> products = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ColorStateList gray = ColorStateList.valueOf(Color.parseColor("#B3B3B3"));

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FilterFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(String param1, String param2) {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_filter, container, false);

        EditText search = v.findViewById(R.id.filterName);
        EditText minPrice = v.findViewById(R.id.filterLowest);
        EditText maxPrice = v.findViewById(R.id.filterHighest);
        CheckBox checkNew = v.findViewById(R.id.checkNew);
        Spinner category = v.findViewById(R.id.spinner2);
        Button filter = v.findViewById(R.id.FilterButton);
        Button clear = v.findViewById(R.id.ClearButton);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.getText().clear();
                minPrice.getText().clear();
                maxPrice.getText().clear();

            }
        });
        category.setAdapter(new ArrayAdapter<ProductCategory>(getContext(), android.R.layout.simple_spinner_dropdown_item, ProductCategory.values()));

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productName.clear();
                boolean used = !checkNew.isChecked();
                System.out.println(used);
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONArray jArray = new JSONArray(response);
                            Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                            products = gson.fromJson(String.valueOf(jArray), type);
                            System.out.println(jArray);
                            for (Product prod : products) {
                                productName.add(prod.name.toString());
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                try {
                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), "Error Terjadi!", Toast.LENGTH_LONG).show();
                        }
                    };
                    StringRequest request = RequestFactory.getProduct(1,5,getLoggedAccount().id,search.getText().toString(),
                            minPrice.getText().toString(), maxPrice.getText().toString(),category.getSelectedItem().toString(),String.valueOf(used),
                            listener, errorListener);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    queue.add(request);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error Terjadi!", Toast.LENGTH_LONG).show();
                }
            }
        });





        return v;
    }
    /**
     * For displaying color change in the textview and text field
     */
    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        EditText filterTextName = getView().findViewById(R.id.filterName);
        TextView filterLabelNama = getView().findViewById(R.id.filterNameLabel);
        filterTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelNama.setTextColor(getResources().getColor(R.color.colorPrimary));
                    filterTextName.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0947E8")));
                    filterLabelNama.setHint("Nama Product");
                } else {
                    filterLabelNama.setHint("");
                    filterLabelNama.setTextColor(getResources().getColor(R.color.GreyText));
                    filterTextName.setBackgroundTintList(gray);
                }
            }
        });

        EditText filterTextLowest = getView().findViewById(R.id.filterLowest);
        TextView filterLabelLowest = getView().findViewById(R.id.filterLowestLabel);
        filterTextLowest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelLowest.setTextColor(getResources().getColor(R.color.colorPrimary));
                    filterTextLowest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0947E8")));
                    filterLabelLowest.setHint("Ex: 1000");
                } else {
                    filterLabelLowest.setHint("");
                    filterLabelLowest.setTextColor(getResources().getColor(R.color.GreyText));
                    filterTextLowest.setBackgroundTintList(gray);
                }
            }
        });

        EditText filterTextHighest = getView().findViewById(R.id.filterHighest);
        TextView filterLabelHighest = getView().findViewById(R.id.filterHighestLabel);
        filterTextHighest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelHighest.setTextColor(getResources().getColor(R.color.colorPrimary));
                    filterTextHighest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0947E8")));
                    filterLabelHighest.setHint("Ex: 1000");
                } else {
                    filterLabelHighest.setHint("");
                    filterLabelHighest.setTextColor(getResources().getColor(R.color.GreyText));
                    filterTextHighest.setBackgroundTintList(gray);
                }
            }
        });
    }


}