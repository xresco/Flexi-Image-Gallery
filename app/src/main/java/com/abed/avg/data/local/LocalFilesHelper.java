package com.abed.avg.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.abed.avg.injection.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalFilesHelper {


    @Inject
    public LocalFilesHelper() {
    }


    public String getFileContent(Context context, String fileName) {
        // Reading json file from assets folder
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String temp;
            while ((temp = br.readLine()) != null)
                sb.append(temp);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close(); // stop reading
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
