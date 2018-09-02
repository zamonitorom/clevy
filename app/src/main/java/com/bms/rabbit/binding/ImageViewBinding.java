package com.bms.rabbit.binding;
// Created by Konstantin on 02.09.2018.

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bms.rabbit.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;

import java.io.File;

public class ImageViewBinding {

    @BindingAdapter({"imageUrl"})
    public static void bindImage(ImageView view, String url) {
//        view.setImageResource(R.drawable.placeholder);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        try {
            Glide.with(view.getContext().getApplicationContext())
                    .load(url)
                    .asBitmap()
                    .encoder(new BitmapEncoder(Bitmap.CompressFormat.PNG,80))
                    .dontTransform()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(200,200)
                    .into(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"imageUrlLarge"})
    public static void bindImage2(ImageView view, String url) {
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(view.getContext().getApplicationContext())
                .load(url)
                .into(view);
    }

    @BindingAdapter({"imageString"})
    public static void bindImageString(ImageView view, String picPath) {
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (picPath != null) {
            Uri uri = Uri.parse(picPath);

            Glide.with(view.getContext().getApplicationContext())
                    .load(new File(uri.getPath()))
                    .override(200,200)
                    .into(view);
        }
    }
}
