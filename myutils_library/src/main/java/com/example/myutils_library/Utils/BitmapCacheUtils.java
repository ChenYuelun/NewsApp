package com.example.myutils_library.Utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;


public class BitmapCacheUtils {


    private NetCacheUtils netCachUtils;

    private LocalCacheUtils localCachUtils;

    private MemoryCacheUtils memoryCachUtils;

    public BitmapCacheUtils(Handler handler) {
        memoryCachUtils = new MemoryCacheUtils();
        localCachUtils = new LocalCacheUtils(memoryCachUtils);
        netCachUtils = new NetCacheUtils(handler, localCachUtils, memoryCachUtils);
    }

    public Bitmap getBitmap(String imageUrl, int position) {
        // 从内存中取图片
        if(memoryCachUtils != null){
            Bitmap bitmap = memoryCachUtils.getBitmapFromMemory(imageUrl);
            if(bitmap != null){
                Log.e("TAG", "图片是从内存获取" + position);
                return  bitmap;
            }
        }

        //从本地文件中取图片
        if (localCachUtils != null) {
            Bitmap bitmap = localCachUtils.getBitmap(imageUrl);
            if (bitmap != null) {
                Log.e("TAG", "图片是从本地获取" + position);
                return bitmap;
            }
        }

        //请求网络图片
        netCachUtils.getBitmapFromNet(imageUrl, position);

        return null;
    }
}
