package com.elbadri.apps.sayaradz.utils;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * Created by Panacea-Soft on 5/8/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


public class Utils {

    public static void setImageToImageView(Context context, ImageView imageView, int drawable) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true);

        Glide.with(context)
                .load(drawable)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }


    public static int getDrawableInt(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    public static void setCircleImageToImageView(final Context context, final ImageView imageView, int drawable, final int borderWidth, final int color) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true)
                .circleCrop();

        if (borderWidth > 0) {
            Glide.with(context)
                    .load(drawable)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            imageView.setImageDrawable(resource);

                            try {
                                int colorContextCompat = ContextCompat.getColor(context, color);


                                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();

                                if (bitmap != null) {

                                    Drawable d = new BitmapDrawable(context.getResources(), getCircularBitmapWithBorder(bitmap, borderWidth, colorContextCompat));

                                    imageView.setImageDrawable(d);
                                } else {
                                    imageView.setImageDrawable(resource);
                                }
                            } catch (Exception e) {
                                Log.e("TEAMPS", "onResourceReady: ", e);
                            }

                        }
                    });
        } else {
            Glide.with(context)
                    .load(drawable)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    public static void setCircleImageToImageView(final Context context, final ImageView imageView, int drawable, final int drawableTintColor, final int borderWidth, final int color) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
                .skipMemoryCache(true)
                .circleCrop();

        if (borderWidth > 0) {
            Glide.with(context)
                    .load(drawable)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                            imageView.setImageDrawable(resource);

                            try {
                                int colorContextCompat = ContextCompat.getColor(context, color);

                                Bitmap bitmap = ((BitmapDrawable) resource).getBitmap();

                                if (bitmap != null) {

                                    Paint paint = new Paint();
                                    paint.setColorFilter(new PorterDuffColorFilter(context.getResources().getColor(drawableTintColor), PorterDuff.Mode.SRC_IN));
                                    Bitmap bitmapResult = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

                                    Canvas canvas = new Canvas(bitmapResult);
                                    canvas.drawBitmap(bitmap, 0, 0, paint);

                                    bitmap = bitmapResult;

                                    Drawable d = new BitmapDrawable(context.getResources(), getCircularBitmapWithBorder(bitmap, borderWidth, colorContextCompat));

                                    imageView.setImageDrawable(d);
                                } else {
                                    imageView.setImageDrawable(resource);
                                }
                            } catch (Exception e) {
                                Log.e("TEAMPS", "onResourceReady: ", e);
                            }

                        }
                    });
        } else {
            Glide.with(context)
                    .load(drawable)
                    .apply(requestOptions)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageView);
        }
    }

    public static Bitmap getCircularBitmapWithBorder(Bitmap bitmap,
                                                     int borderWidth, int color) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(color);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
        return canvasBitmap;
    }

}