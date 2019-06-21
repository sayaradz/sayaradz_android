package com.sayaradz.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


object Utils {

    fun setImageToImageView(context: Context, imageView: ImageView, drawable: Int) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
            .skipMemoryCache(true)

        Glide.with(context)
            .load(drawable)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }


    fun getDrawableInt(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
/*
    fun setCircleImageToImageView(
        context: Context,
        imageView: ImageView,
        drawable: Int,
        borderWidth: Int,
        color: Int
    ) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
            .skipMemoryCache(true)
            .circleCrop()

        if (borderWidth > 0) {
            Glide.with(context)
                .load(drawable)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                        imageView.setImageDrawable(resource)

                        try {
                            val colorContextCompat = ContextCompat.getColor(context, color)


                            val bitmap = (resource as BitmapDrawable).bitmap

                            if (bitmap != null) {

                                val d = BitmapDrawable(
                                    context.resources,
                                    getCircularBitmapWithBorder(bitmap, borderWidth, colorContextCompat)
                                )

                                imageView.setImageDrawable(d)
                            } else {
                                imageView.setImageDrawable(resource)
                            }
                        } catch (e: Exception) {
                            Log.e("TEAMPS", "onResourceReady: ", e)
                        }

                    }
                })
        } else {
            Glide.with(context)
                .load(drawable)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    fun setCircleImageToImageView(
        context: Context,
        imageView: ImageView,
        drawable: Int,
        drawableTintColor: Int,
        borderWidth: Int,
        color: Int
    ) {
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE) // because file name is always same
            .skipMemoryCache(true)
            .circleCrop()

        if (borderWidth > 0) {
            Glide.with(context)
                .load(drawable)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {

                        imageView.setImageDrawable(resource)

                        try {
                            val colorContextCompat = ContextCompat.getColor(context, color)

                            var bitmap: Bitmap? = (resource as BitmapDrawable).bitmap

                            if (bitmap != null) {

                                val paint = Paint()
                                paint.colorFilter = PorterDuffColorFilter(
                                    context.resources.getColor(drawableTintColor),
                                    PorterDuff.Mode.SRC_IN
                                )
                                val bitmapResult =
                                    Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

                                val canvas = Canvas(bitmapResult)
                                canvas.drawBitmap(bitmap, 0f, 0f, paint)

                                bitmap = bitmapResult

                                val d = BitmapDrawable(
                                    context.resources,
                                    getCircularBitmapWithBorder(bitmap, borderWidth, colorContextCompat)
                                )

                                imageView.setImageDrawable(d)
                            } else {
                                imageView.setImageDrawable(resource)
                            }
                        } catch (e: Exception) {
                            Log.e("TEAMPS", "onResourceReady: ", e)
                        }

                    }
                })
        } else {
            Glide.with(context)
                .load(drawable)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
        }
    }

    fun getCircularBitmapWithBorder(
        bitmap: Bitmap?,
        borderWidth: Int, color: Int
    ): Bitmap? {
        if (bitmap == null || bitmap.isRecycled) {
            return null
        }

        val width = bitmap.width + borderWidth
        val height = bitmap.height + borderWidth

        val canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = shader

        val canvas = Canvas(canvasBitmap)
        val radius = if (width > height) height.toFloat() / 2f else width.toFloat() / 2f
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)
        paint.shader = null
        paint.style = Paint.Style.STROKE
        paint.color = color
        paint.strokeWidth = borderWidth.toFloat()
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius - borderWidth / 2, paint)
        return canvasBitmap
    }
        */

}