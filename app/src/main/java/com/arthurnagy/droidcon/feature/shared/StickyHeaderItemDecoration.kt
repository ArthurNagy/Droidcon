package com.arthurnagy.droidcon.feature.shared

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidcon.HeaderItemBinding
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.util.dimension
import com.arthurnagy.droidcon.util.drawable

abstract class StickyHeaderItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private var headerBinding: HeaderItemBinding? = null
    private val headerHeight = context.dimension(R.dimen.header_height)
    private val shadowHeight = context.dimension(R.dimen.header_elevation)
    private val shadow = context.drawable(R.drawable.bg_bottom_shadow)!!

    abstract fun isHeader(position: Int): Boolean

    abstract fun getHeaderTitle(position: Int): String

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (isHeader(parent.getChildAdapterPosition(view))) {
            outRect.top = headerHeight
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        super.onDrawOver(canvas, parent, state)
        if (headerBinding == null) {
            DataBindingUtil.inflate<HeaderItemBinding>(LayoutInflater.from(parent.context), R.layout.item_header, parent, false).let {
                it.root.measure(ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY),
                        parent.paddingLeft + parent.paddingRight, it.root.layoutParams.width),
                        ViewGroup.getChildMeasureSpec(View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED),
                                parent.paddingTop + parent.paddingBottom, it.root.layoutParams.height))
                it.root.layout(0, 0, it.root.measuredWidth, it.root.measuredHeight)
                headerBinding = it
            }
        }
        var previousHeader = ""
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            val title = getHeaderTitle(position)
            headerBinding?.let {
                it.title.text = title
                if (previousHeader != title || isHeader(position)) {
                    drawHeader(canvas, child, it.root)
                    previousHeader = title
                }
            }
        }
    }

    private fun drawHeader(canvas: Canvas, child: View, headerView: View) {
        canvas.save()
        canvas.translate(0f, Math.max(0, child.top - headerView.height).toFloat())
        headerView.draw(canvas)
        canvas.restore()
        val shadowPosition = Math.max(child.top, headerView.bottom)
        shadow.setBounds(0, shadowPosition, headerView.right, shadowPosition + shadowHeight)
        shadow.alpha = Math.max(0, Math.min(255, (((2 * headerHeight - child.top).toFloat() / headerHeight.toFloat()) * 255).toInt()))
        shadow.draw(canvas)
    }
}