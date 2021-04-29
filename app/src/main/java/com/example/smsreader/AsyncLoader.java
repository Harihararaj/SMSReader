package com.example.smsreader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class AsyncLoader extends AsyncTaskLoader<ArrayList<Sms>> {
    ArrayList<Sms> sms=new ArrayList<>();
    Context context;
    public AsyncLoader(@NonNull Context context) {
        super(context);
        this.context=context;
        Log.d("Constructor","--------------------------------------");
    }

    @Nullable
    @Override
    public ArrayList<Sms> loadInBackground() {

        Cursor c = getContext().getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {

            String mobile = c.getString(c.getColumnIndexOrThrow("address"));

            String message = c.getString(c.getColumnIndexOrThrow("body"));


            sms.add(new Sms(mobile, message));
            c.moveToNext();
        }
        c.close();
        //Toast.makeText(context, "LoadInBackground", Toast.LENGTH_SHORT).show();
        Log.d("LoadInBackground","--------------------------------------");
        return sms;
    }

    @Override
    protected void onStartLoading() {
        Log.d("onStartLoading","--------------------------------------");
        forceLoad();
    }

}
