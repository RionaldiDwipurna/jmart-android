package RionaldiJmartFH.jmart_android;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilterFragment.
     */
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
        return inflater.inflate(R.layout.fragment_filter, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        EditText filterTextName = getView().findViewById(R.id.filterName);
        TextView filterLabelNama = getView().findViewById(R.id.filterNameLabel);
        filterTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    filterLabelNama.setTextColor(getResources().getColor(R.color.purple_200));
                    filterTextName.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB86FC")));
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
                    filterLabelLowest.setTextColor(getResources().getColor(R.color.purple_200));
                    filterTextLowest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB86FC")));
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
                    filterLabelHighest.setTextColor(getResources().getColor(R.color.purple_200));
                    filterTextHighest.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFBB86FC")));
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