package com.example.smsreader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Sms>> {

    boolean countOfPermissionDialog=true;
    int id=0;
    int flag;
    RecyclerAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    protected void onResume() {
        super.onResume();
        checkingForPermission();
    }

    public void checkingForPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED && countOfPermissionDialog) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 4);
            countOfPermissionDialog=false;
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED && !countOfPermissionDialog) {
            displayDialog();

        } else {
            flag=0;
            ReadSms();
        }
    }

    public void displayDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("This App cannot be Accessed without Your permission. If you want to continue with the app press 'Yes' or to close app press 'No'");
        builder.setTitle("Permission Denied");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (dialog, which) -> {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 4);
            dialog.cancel();
        });

        builder.setNegativeButton("No", (dialog, which) -> finish());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.refresh) {
            ReadSms();
        }
        return true;
    }

    public void ReadSms() {
        id++;
        Log.d("ReadSms","--------------------------------------");
        LoaderManager.getInstance(this).initLoader(id,null,this);


    }
    @NonNull
    @Override
    public Loader<ArrayList<Sms>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("onCreateLoader","--------------------------------------");
        return new AsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Sms>> loader, ArrayList<Sms> data) {
        if(flag==0) {

            recyclerView = findViewById(R.id.recyclerview);
            adapter = new RecyclerAdapter(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            flag=1;
        } else{

            adapter.setter(data);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
        LoaderManager.getInstance(this).destroyLoader(id);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Sms>> loader) {
        Log.d("onLoaderReset","--------------------------------------");
    }


}







