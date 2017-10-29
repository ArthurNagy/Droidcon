package com.arthurnagy.droidcon.feature.shared

import android.content.Context
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.model.Term
import com.arthurnagy.droidcon.util.dimension

@BindingMethods(BindingMethod(type = TermsView::class, attribute = "terms", method = "setTerms"))
class TermsView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {
    val padding = context.dimension(R.dimen.content_padding)

    fun setTerms(terms: List<Term>?) {
        removeAllViews()
        terms?.forEach {
            val textView = createTermsItem(it)
            addView(textView)
            val layoutParams = textView.layoutParams as LinearLayout.LayoutParams
            layoutParams.marginEnd = padding
            layoutParams.setMargins(0, 0, padding, 0)
            textView.layoutParams = layoutParams
        }
    }

    private fun createTermsItem(term: Term): TextView {
        val textView = TextView(context)
        textView.setPadding(padding, padding, padding, padding)
        textView.text = term.name
        textView.setBackgroundColor(getColor(term.name))
        return textView
    }

    @ColorInt
    private fun getColor(title: String): Int {
        var hue = title.length * 10f
        while (hue > 360) {
            hue -= 360
        }
        return ColorUtils.HSLToColor(arrayOf(hue, 0.6f, 0.8f).toFloatArray())
    }
}