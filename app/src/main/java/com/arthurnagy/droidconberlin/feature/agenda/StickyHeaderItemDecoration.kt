package com.arthurnagy.droidconberlin.feature.agenda

import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.HeaderItemBinding

import com.arthurnagy.droidconberlin.R

class StickyHeaderItemDecoration(
        private val headerOffset: Int,
        private val sticky: Boolean,
        private val sectionCallback: SectionCallback) : RecyclerView.ItemDecoration() {
    private var headerBinding: HeaderItemBinding? = null

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        if (sectionCallback.isSection(pos)) {
            outRect.top = headerOffset
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDrawOver(canvas, parent, state)
        if (headerBinding == null) {
            DataBindingUtil.inflate<HeaderItemBinding>(LayoutInflater.from(parent.context),R.layout.item_header, parent, false).let {
                it.root.measure(ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                        parent.paddingLeft + parent.paddingRight, it.root.layoutParams.width),
                        ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED),
                                parent.paddingTop + parent.paddingBottom, it.root.layoutParams.height))
                it.root.layout(0, 0, it.root.measuredWidth, it.root.measuredHeight)
                headerBinding = it
            }
        }

        var previousHeader: CharSequence = ""
        for (i in 0..parent.childCount - 1) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            val title = "Header for ${sectionCallback.getSectionHeader(position)}"
            headerBinding?.let {
                it.title.setText(title)
                if (previousHeader != title || sectionCallback.isSection(position)) {
                    drawHeader(canvas, child, it.root)
                    previousHeader = title
                }
            }
        }
    }

    private fun drawHeader(canvas: Canvas, child: View, headerView: View) {
        canvas.save()
        canvas.translate(0f, (if (sticky) Math.max(0, child.top - headerView.height) else (child.top - headerView.height)).toFloat())
        headerView.draw(canvas)
        canvas.restore()
    }

    interface SectionCallback {

        fun isSection(position: Int): Boolean

        fun getSectionHeader(position: Int): CharSequence
    }
}