package com.klibre.utils

import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import android.widget.TextView



/**
 * Created by Miguel on 29/1/2018.
 */

object ColorSnackBar {

    private fun getSnackBarLayout(snackbar: Snackbar?): View? {
        return snackbar?.view
    }

    private fun colorSnackBar(snackbar: Snackbar, colorBackgroundId: Int, color_text: Int): Snackbar? {
        val snackBarView = getSnackBarLayout(snackbar)
        if (snackBarView != null) {
            snackBarView!!.setBackgroundColor(colorBackgroundId)

            val textView = snackBarView!!
                    .findViewById<TextView>(android.support.design.R.id.snackbar_text)
            textView.setTextColor(color_text)
            textView.gravity = Gravity.CENTER
        }

        return snackbar
    }

    fun info(snackbar: Snackbar, colorBackgroundId: Int, colorTextId: Int): Snackbar? {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId)
    }

    fun warning(snackbar: Snackbar, colorBackgroundId: Int, colorTextId: Int): Snackbar? {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId)
    }

    fun alert(snackbar: Snackbar, colorBackgroundId: Int, colorTextId: Int): Snackbar? {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId)
    }

    fun confirm(snackbar: Snackbar, colorBackgroundId: Int, colorTextId: Int): Snackbar? {
        return colorSnackBar(snackbar, colorBackgroundId, colorTextId)
    }
}