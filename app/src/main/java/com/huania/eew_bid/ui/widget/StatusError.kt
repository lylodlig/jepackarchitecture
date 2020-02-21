package com.kuky.demo.wan.android.ui.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.huania.eew_bid.R
import com.huania.eew_bid.ui.widget.ErrorReload

/**
 * @author kuky.
 * @description
 */

class StatusError @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CenterDrawableTextView(context, attrs, defStyleAttr) {

    var errorReload: ErrorReload? = null

    init {
        val errDrawable = ContextCompat.getDrawable(context, R.drawable.tag_load_error)
        errDrawable?.setBounds(0, 0, errDrawable.minimumWidth / 2, errDrawable.minimumHeight / 2)
        compoundDrawablePadding = 12
        setCompoundDrawables(null, errDrawable, null, null)
//        text = resources.getString(R.string.reload_data)
        text = "重新加载"
        setTextColor(Color.parseColor("#FFCCCCCC"))
        setOnClickListener { errorReload?.reload() }
    }
}