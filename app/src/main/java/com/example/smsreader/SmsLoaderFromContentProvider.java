package com.example.smsreader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class SmsLoaderFromContentProvider extends AsyncTaskLoader<ArrayList<Sms>> {
    ArrayList<Sms> smsList =new ArrayList<>();
    public SmsLoaderFromContentProvider(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public ArrayList<Sms> loadInBackground() {
        if(smsList.size()>0){
            smsList.clear();
        }
        Cursor smsContentCursor = getContext().getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        smsContentCursor.moveToFirst();
        String mobile = null,message = null,type = null;
        for (int i = 0; i < smsContentCursor.getCount(); i++) {
            try {
                mobile = smsContentCursor.getString(smsContentCursor.getColumnIndexOrThrow("address"));
            }
            catch (IllegalStateException e){
                e.getStackTrace();
            }
            try {
                message = smsContentCursor.getString(smsContentCursor.getColumnIndexOrThrow("body"));
            }
            catch (IllegalStateException e) {
                e.getStackTrace();
            }
            try {

                type = smsContentCursor.getString(smsContentCursor.getColumnIndexOrThrow("type"));
            }
            catch (IllegalStateException e){
                e.getStackTrace();
            }


            smsList.add(new Sms(mobile, message,type));
            smsContentCursor.moveToNext();
        }
        smsContentCursor.close();
        return smsList;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
