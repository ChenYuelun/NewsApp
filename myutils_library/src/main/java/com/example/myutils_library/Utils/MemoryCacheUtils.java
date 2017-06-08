package com.example.myutils_library.Utils;

import android.graphics.Bitmap;
import android.util.LruCache;


public class MemoryCacheUtils {

    private LruCache<String ,Bitmap> lruCache;

    public MemoryCacheUtils(){
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        lruCache = new LruCache<String,Bitmap>(maxSize){

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }


    public void putBitmap2Memory(String imageUrl, Bitmap bitmap) {
        lruCache.put(imageUrl,bitmap);
    }

    public Bitmap getBitmapFromMemory(String imageUrl) {
        return lruCache.get(imageUrl);
    }
}
