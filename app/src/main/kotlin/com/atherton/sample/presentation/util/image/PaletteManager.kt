package com.atherton.sample.presentation.util.image

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette
import com.atherton.sample.R
import com.atherton.sample.presentation.util.extension.getColorCompat
import com.atherton.sample.util.injection.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaletteManager @Inject constructor(@param:ApplicationContext private val context: Context) {

    private val lruCache = LruCache<String, ColorCombination>(MAX_PALETTE_ENTRIES)

    private val defaultColorCombination: ColorCombination by lazy {
        ColorCombination(
            backgroundColor = context.getColorCompat(R.color.colorSurface),
            textColor = context.getColorCompat(R.color.colorOnSurface)
        )
    }

    fun colorCombinationForImage(imageUrl: String?, bitmap: Bitmap?, onLoaded: (ColorCombination) -> Unit) {
        if (imageUrl == null || bitmap == null) {
            onLoaded(defaultColorCombination)
            return
        }

        val cached = lruCache[imageUrl]
        if (cached != null) {
            onLoaded(cached)
        } else {
            Palette.Builder(bitmap)
                .generate { palette ->
                    val swatch: Palette.Swatch? = palette?.darkVibrantSwatch
                    val backgroundColor = swatch?.rgb ?: defaultColorCombination.backgroundColor

                    val colorCombination = ColorCombination(
                        backgroundColor = backgroundColor,
                        textColor = defaultColorCombination.textColor
                    )
                    lruCache.put(imageUrl, colorCombination)
                    onLoaded(colorCombination)
                }
        }
    }

    companion object {
        private const val MAX_PALETTE_ENTRIES = 100
    }
}

data class ColorCombination(@ColorInt val backgroundColor: Int, @ColorInt val textColor: Int)
