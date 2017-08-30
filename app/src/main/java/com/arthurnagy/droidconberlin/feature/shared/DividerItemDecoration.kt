package com.arthurnagy.droidconberlin.feature.shared

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View


class DividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val divider: Drawable

    init {
        val attributes = context.obtainStyledAttributes(intArrayOf(android.R.attr.listDivider))
        divider = attributes.getDrawable(0)
        attributes.recycle()
    }

    fun shouldDrawDecoration(childPosition: Int, recyclerView: RecyclerView) = childPosition < recyclerView.adapter.itemCount - 1

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        if (parent.layoutManager == null || parent.itemAnimator != null && parent.itemAnimator.isRunning) {
            return
        }
        canvas.save()
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.adapter.itemCount
        if (childCount > 1) {
            for (childPosition in 0 until childCount - 1) {
                parent.layoutManager.getChildAt(childPosition)?.let {
                    val top = it.bottom + (it.layoutParams as RecyclerView.LayoutParams).bottomMargin
                    if (shouldDrawDecoration(parent.getChildAdapterPosition(it), parent)) {
                        divider.setBounds(left, top, right, top + divider.intrinsicHeight + it.translationY.toInt())
                        divider.draw(canvas)
                    }
                }
            }
        }
        canvas.restore()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        outRect.bottom = divider.intrinsicHeight
    }
}