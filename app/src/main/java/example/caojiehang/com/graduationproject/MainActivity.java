package example.caojiehang.com.graduationproject;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import  android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import example.caojiehang.com.graduationproject.connectservice.SocketService;
import example.caojiehang.com.graduationproject.fragment.ConnectFrament;
import example.caojiehang.com.graduationproject.fragment.DataFragment;
import example.caojiehang.com.graduationproject.fragment.DeviceMonitorFragment;

public class MainActivity extends AppCompatActivity {

    private static final String CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW";
    /*   private  Intent intent;*/

    private DrawerLayout drawerLayout;
    private Fragment currentFragment = new Fragment();
    private FragmentManager fragmentManager;
    private List<Fragment> fragments = new ArrayList<>();
    private int currentIndex = 0;
    ServiceConnection connection ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_line_weight_black_24dp);
        }
        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT);
            fragments.removeAll(fragments);
            fragments.add(fragmentManager.findFragmentByTag(0+""));
            fragments.add(fragmentManager.findFragmentByTag(1+""));
            fragments.add(fragmentManager.findFragmentByTag(2+""));
            restoreFragment();
        } else {
            fragments.add(new DeviceMonitorFragment());
            fragments.add(new DataFragment());
            fragments.add(new ConnectFrament());

            showFragment();
        }
        navView.setCheckedItem(R.id.nav_device);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_device:
                    drawerLayout.closeDrawers();
                        currentIndex = 0;
                        showFragment();
                    break;
                    case R.id.nav_data:
                        drawerLayout.closeDrawers();
                        currentIndex = 1;
                        showFragment();
                    break;
                    case R.id.nav_connection:
                        drawerLayout.closeDrawers();
                        currentIndex = 2;
                        showFragment();
                       break;
                    case R.id.nav_close:
                        Intent intent1 = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        default:
                }
                return true;
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURRENT_FRAGMENT,currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

        }
        return true;
    }
    private void showFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(!fragments.get(currentIndex).isAdded()) {
            transaction.hide(currentFragment)
                    .add(R.id.fil_content,fragments.get(currentIndex));
        } else {
            transaction.hide(currentFragment)
                    .show(fragments.get(currentIndex));
        }
        currentFragment = fragments.get(currentIndex);
        transaction.commit();
    }
    private void restoreFragment() {
        FragmentTransaction mBeginTreansaction = fragmentManager.beginTransaction();
        for (int i = 0; i <fragments.size();i++) {
            if(i == currentIndex) {
                mBeginTreansaction.show(fragments.get(i));
            }else {
                mBeginTreansaction.hide(fragments.get(i));
            }
        }
        mBeginTreansaction.commit();
        currentFragment = fragments.get(currentIndex);
    }


   /* @Override
    public void submit(String etIp, int etPort) {
            intent = new Intent(this,SocketService.class);
            intent.putExtra("ipAddress", etIp);
            intent.putExtra("portNumber", etPort);
    }*/
}
