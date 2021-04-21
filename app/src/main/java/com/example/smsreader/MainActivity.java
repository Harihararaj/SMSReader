package com.example.smsreader;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int countOfPermissionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    protected void onResume() {
        super.onResume();
        checkingForPermission();
    }
    public void checkingForPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED && countOfPermissionDialog == 0) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 4);
            countOfPermissionDialog++;
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_DENIED && countOfPermissionDialog != 0) {
            displayDialog();

        }
        else{
            ReadSms();
        }
    }
    public void displayDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("This App cannot be Accessed without Your permission. If you want to continue with the app press 'Yes' or to close app press 'No'");
        builder.setTitle("Permission Denied");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, 4);
                dialog.cancel();
            }

        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.refresh){
            setContentView(R.layout.activity_main);
            Toolbar toolbar=findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ReadSms();
        }
        return true;
    }

        public void ReadSms() {
            ArrayList<Sms> sms=new ArrayList<>();
            MySync mySync=new MySync();
            mySync.execute(sms);
        }

        class MySync extends AsyncTask<ArrayList<Sms>,Integer,ArrayList<Sms>>{


        @Override
        protected ArrayList<Sms> doInBackground(ArrayList<Sms>... arrayLists) {
            ArrayList<Sms> sms=arrayLists[0];
            Cursor c = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            c.moveToFirst();

            for (int i = 0; i < c.getCount(); i++) {

                String mobile = c.getString(c.getColumnIndexOrThrow("address")).toString();

                String message = c.getString(c.getColumnIndexOrThrow("body")).toString();

                sms.add(new Sms(mobile,message));
                c.moveToNext();
            }
            //adapter.notifyDataSetChanged();
            return sms;
        }

    @Override
    protected void onPostExecute(ArrayList<Sms> sms) {
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        RecyclerAdapter adapter=new RecyclerAdapter();
        adapter.setter(sms);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}
}