package com.sawelo.passdraw.ui.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.widget.ScrollView
import com.sawelo.passdraw.R
import com.sawelo.passdraw.util.BackButtonListener

class DialogWindowScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
): ScrollView (context, attributeSet) {
    private var backButtonListener: BackButtonListener? = null

    fun setBackButtonListener(listener: BackButtonListener) {
        backButtonListener = listener
    }

    init {
        setBackgroundResource(R.drawable.rounded_background)
        elevation = 16F
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK) {
            backButtonListener?.onBackButtonListener()
            return true
        }
        return super.dispatchKeyEvent(event)
    }
}