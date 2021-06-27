package com.radomirribic.trenutnovremeusrbiji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.radomirribic.trenutnovremeusrbiji.fragments.AutomaticStationsFragment;


public class MainActivity extends AppCompatActivity  {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //downloadRss();
        setContentView(R.layout.activity_main);
        setTitle("Vreme u Srbiji");
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
        showMainListFragment();
    }

    private void showMainListFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.my_frame_layout, AutomaticStationsFragment.newInstance());
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.itemRefresh){
            showMainListFragment();
            Toast.makeText(this, "Refreshed data", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }


}