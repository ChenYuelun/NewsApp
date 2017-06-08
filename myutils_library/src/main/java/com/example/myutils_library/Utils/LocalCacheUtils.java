package com.example.myutils_library.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class LocalCacheUtils {
    private final MemoryCacheUtils momenyCachUtils;

    public LocalCacheUtils(MemoryCacheUtils momenyCachUtils) {
        this.momenyCachUtils = momenyCachUtils;
    }

    public void putBitmap2Local(String imageUrl, Bitmap bitmap) {
        try {
            String dir = Environment.getExternalStorageDirectory()+"/beijingnews";
            //文件名称
            String fileName = MD5Encoder.encode(imageUrl);
            File file = new File(dir, fileName);
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }

            Log.e("TAG","file=="+file.getAbsolutePath());

            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 获取图片
     *
     * @param imageUrl
     * @return
     */
    public Bitmap getBitmap(String imageUrl) {
        try {
            String dir = Environment.getExternalStorageDirectory()+"/beijingnews";
            String fileName = MD5Encoder.encode(imageUrl);
            File file = new File(dir, fileName);

            if (file.exists()) {

                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                if(bitmap != null){
                    momenyCachUtils.putBitmap2Memory(imageUrl,bitmap);
                }

                return bitmap;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
