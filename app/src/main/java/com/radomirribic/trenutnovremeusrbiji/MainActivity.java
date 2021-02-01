package com.radomirribic.trenutnovremeusrbiji;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.radomirribic.trenutnovremeusrbiji.utils.AsyncTaskListener;
import com.radomirribic.trenutnovremeusrbiji.utils.DownloadData;

public class MainActivity extends AppCompatActivity implements AsyncTaskListener {

    private String timeStamp;


    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadRss();
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        //setTitle("Vreme u Srbiji");






    }

    private void downloadRss(){
        DownloadData downloadData=new DownloadData(this);
        downloadData.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.itemRefresh){
            downloadRss();
            Context context;
            CharSequence text;
            Toast.makeText(this, "Refreshed data", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void giveView(View view) {

    }
}