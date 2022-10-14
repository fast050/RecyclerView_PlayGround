package com.example.recyclerviewplayground.recycler_item_decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(
    context: Context,
    resId: Int,
    private val size: Int,
    private val edgeEnabled: Boolean = false
) : RecyclerView.ItemDecoration() {

    private var mDivider: Drawable = ContextCompat.getDrawable(context, resId)!!
    private val NO_SPACING = 0
    private val BOTTOM_SPACING = size

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

        // left margin for the divider
        val dividerLeft: Int = 0

        // right margin for the divider with
        // reference to the parent width
        val dividerRight: Int = parent.width - 0

        // this loop creates the top and bottom
        // divider for each items in the RV
        // as each items are different
        for (i in 0 until parent.childCount) {

            // this condition is because the last
            // and the first items in the RV have
            // no dividers in the list
            if (i != parent.childCount - 1) {
                val child: View = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                // calculating the distance of the
                // divider to be drawn from the top
                val dividerTop: Int = child.bottom + params.bottomMargin + size/2
                val dividerBottom: Int = dividerTop + mDivider.intrinsicHeight

                mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
                mDivider.draw(c)
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Basic item positioning
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount    //count of the recycler view item
        val isLastPosition = position == (itemCount - 1)
        val isFirstPosition = position == 0


        Log.d("TAG", "getItemOffsets: position=$position , itemCount=$itemCount ")

        // Saved size
        val sizeBasedOnEdge = if (edgeEnabled) size else NO_SPACING
        val sizeBasedOnFirstPosition = if (isFirstPosition) sizeBasedOnEdge else size
        val sizeBasedOnLastPosition = if (isLastPosition) BOTTOM_SPACING else NO_SPACING // NO_SPACING means zero

        // Update properties
        with(outRect) {
            left = sizeBasedOnEdge
            top = sizeBasedOnFirstPosition
            right = sizeBasedOnEdge
            bottom = sizeBasedOnLastPosition
        }
    }
}