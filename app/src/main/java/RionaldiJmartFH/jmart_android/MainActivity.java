package RionaldiJmartFH.jmart_android;

import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
/**
 *  The main activity after logged in
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class MainActivity extends AppCompatActivity{
    // creating constant keys for shared preferences.
    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL_KEY = "email_key";

    // key for storing password.
    public static final String PASSWORD_KEY = "password_key";

    // variable for shared preferences.
    SharedPreferences sharedpreferences;
    String email;


    ViewPager2 viewpager2;
    TabLayout tablayout;

    /**
     *  Display the filter and product tab
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();

        // initializing our shared preferences.
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

        // getting data from shared prefs and
        // storing it in our string variable.
        email = sharedpreferences.getString(EMAIL_KEY, null);

        viewpager2 = findViewById(R.id.viewPager);
        tablayout = findViewById(R.id.simpleTabLayout);

        viewpager2.setAdapter(new FragmentAdapter(this));

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tablayout, viewpager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Products");
                        break;
                    case 1:
                        tab.setText("Filter");
                        break;

                }

            }
        });tabLayoutMediator.attach();

    }

    /**
     *  If the store already created, the menu bar will display the add item section
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (getLoggedAccount().store == null) {
            menu.findItem(R.id.AddItem).setVisible(false);
        }
        return true;
    }

    /**
     *  Logic when the menu items are clicked
     */
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()) {
            case R.id.person:
                Intent intent = new Intent(this, AboutMeActivity.class);
                this.startActivity(intent);
                break;
            case R.id.AddItem:
                Intent intent2 = new Intent(this, CreateProductActivity.class);
                this.startActivity(intent2);
                break;
            case R.id.logOut:
                SharedPreferences.Editor editor = sharedpreferences.edit();

                // below line will clear
                // the data in shared prefs.
                editor.clear();

                // below line will apply empty
                // data to shared prefs.
                editor.apply();

                // starting mainactivity after
                // clearing values in shared preferences.
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

}
//test12
