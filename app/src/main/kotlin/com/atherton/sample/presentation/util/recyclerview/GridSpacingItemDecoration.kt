package com.atherton.sample.presentation.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *  Provides uniform spacingInPixels to items within a RecyclerView when using a GridLayoutManager.
 *
 *  @param numColumns the span count of the GridLayoutManager
 *  @param spacingInPixels pixels to space by. Can use getResources().getDimensionPixelSize(R.dimen.grid_spacing) to
 *                         convert from DP to PX
 *  @param shouldSpaceEdges whether to apply spacingInPixels to the outer edges of RecyclerView
 *  @param numOfHeaders specify if any headers are present in the RecyclerView
 */
class GridSpacingItemDecoration(
    private val numColumns: Int,
    private val spacingInPixels: Int,
    private val shouldSpaceEdges: Boolean = false,
    private val numOfHeaders: Int = 0
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) - numOfHeaders // item position

        if (position >= 0) {
            val currentColumn = position % numColumns

            outRect.apply {
                if (shouldSpaceEdges) {
                    left = spacingInPixels - currentColumn * spacingInPixels / numColumns
                    right = (currentColumn + 1) * spacingInPixels / numColumns

                    if (position < numColumns) {
                        top = spacingInPixels
                    }
                    bottom = spacingInPixels
                } else {
                    left = currentColumn * spacingInPixels / numColumns
                    right = spacingInPixels - (currentColumn + 1) * spacingInPixels / numColumns
                    if (position >= numColumns) {
                        top = spacingInPixels
                    }
                }
            }
        } else {
            outRect.apply {
                left = 0
                right = 0
                top = 0
                bottom = 0
            }
        }
    }
}
