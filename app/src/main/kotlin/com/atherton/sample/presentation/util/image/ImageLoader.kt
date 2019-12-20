package com.atherton.sample.presentation.util.image

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.atherton.sample.R
import com.atherton.sample.presentation.util.glide.GlideRequests
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageLoader @Inject constructor(private val paletteManager: PaletteManager) {

    fun loadContentListItem(
        with: GlideRequests,
        url: String?,
        into: ImageView,
        onSuccess: (colorCombination: ColorCombination) -> Unit
    ) {
        with.asBitmap()
            .load(url)
            .apply(listItemRequestOptions)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean = false // we return false here so that any error placeholders are automatically handled

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    paletteManager.colorCombinationForImage(url, resource) { colorCombination ->
                        onSuccess.invoke(colorCombination)
                    }
                    return false // we need to return false for .into() to work below
                }
            })
            .into(into)
    }

    fun load(
        with: GlideRequests,
        url: String?,
        requestOptions: RequestOptions? = null,
        into: ImageView
    ) {
        with.load(url).apply {
            if (requestOptions != null) {
                apply(requestOptions)
            }
        }.into(into)
    }

    fun load(
        with: GlideRequests,
        @DrawableRes drawableResId: Int,
        requestOptions: RequestOptions? = null,
        into: ImageView
    ) {
        with.load(drawableResId).apply {
            if (requestOptions != null) {
                apply(requestOptions)
            }
        }.into(into)
    }

    fun clear(with: GlideRequests, imageView: ImageView) {
        with.clear(imageView)
    }

    companion object {

        private const val listItemRadius = 12

        val listItemRequestOptions: RequestOptions by lazy {
            RequestOptions()
                .transform(CenterCrop(), RoundedCorners(listItemRadius))
                .error(R.drawable.ic_broken_image_white_24dp)
        }
    }
}
