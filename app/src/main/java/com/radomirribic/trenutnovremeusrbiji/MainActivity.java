package com.radomirribic.trenutnovremeusrbiji;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.radomirribic.trenutnovremeusrbiji.utils.AsyncTaskListener;
import com.radomirribic.trenutnovremeusrbiji.utils.DownloadData;

public class MainActivity extends AppCompatActivity implements AsyncTaskListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadRss();


    }

    private void downloadRss(){
        DownloadData downloadData=new DownloadData(this);
        downloadData.execute();

    }

    @Override
    public void giveView(View view) {

    }
}