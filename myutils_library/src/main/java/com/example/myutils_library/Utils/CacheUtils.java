package com.example.myutils_library.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by chenyuelun on 2017/6/2.
 */

public class CacheUtils {

    public static void setBooleanForGuide(Context context, String key,boolean values) {
        SharedPreferences news = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        news.edit().putBoolean(key,values).commit();
    }

    public static boolean getBooleanForGuide(Context context, String key) {
        SharedPreferences news = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        return news.getBoolean(key,false);

    }

    public static void putStringData(Context context, String key, String values) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileOutputStream fos = null;
            try {
                String dir = Environment.getExternalStorageDirectory() + "/beijingNews/files";
                String fileName = MD5Encoder.encode(key);
                File file = new File(dir, fileName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                if (!file.exists()) {
                    file.createNewFile();

                }

                fos = new FileOutputStream(file);
                fos.write(values.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        } else {
            SharedPreferences sp = context.getSharedPreferences("beijingnews", Context.MODE_PRIVATE);
            sp.edit().putString(key, values).commit();
        }
    }

    public static String getStringData(Context context, String key) {
        String cache = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            FileInputStream fis = null;
            ByteArrayOutputStream baos = null;
            try {
                String dir = Environment.getExternalStorageDirectory() + "/beijingNews/files";
                String fileName = MD5Encoder.encode(key);
                File file = new File(dir, fileName);
                if(file.exists()) {
                    fis = new FileInputStream(file);
                    baos = new ByteArrayOutputStream();
                    int length;
                    byte[] buffer = new byte[1024];
                    while((length = fis.read(buffer)) != -1) {
                        baos.write(buffer,0,length);
                    }

                    cache = baos.toString();

                }



            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(baos != null) {
                    try {
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            SharedPreferences sp = context.getSharedPreferences("beijingnews", Context.MODE_PRIVATE);
            cache =  sp.getString(key, "");
        }

        return cache;

    }
}
