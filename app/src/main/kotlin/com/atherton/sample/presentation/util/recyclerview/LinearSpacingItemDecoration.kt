package com.atherton.sample.presentation.util.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingItemDecoration(
    private val spacingInPixels: Int,
    private val orientation: Orientation
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }

        when (orientation) {
            is Orientation.Horizontal -> outRect.left = spacingInPixels
            is Orientation.Vertical -> outRect.top = spacingInPixels
        }
    }

    sealed class Orientation {
        object Horizontal : Orientation()
        object Vertical : Orientation()
    }
}
