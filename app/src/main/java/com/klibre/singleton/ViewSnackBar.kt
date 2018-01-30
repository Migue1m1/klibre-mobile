package com.klibre.singleton

import android.os.Build
import android.widget.TextView
import android.support.design.widget.Snackbar
import android.view.View
import com.klibre.R
import com.klibre.utils.ColorSnackBar


/**
 * Created by Miguel on 29/1/2018.
 */

class ViewSnackBar private constructor() {
    private lateinit var snackbar: Snackbar

    fun viewSnackBar(view: View, msgWs: String) {

        snackbar = Snackbar.make(view, msgWs, 3000)
        ColorSnackBar.alert(snackbar,
                view.getContext().getResources().getColor(R.color.colorPrimaryLight),
                view.getContext().getResources().getColor(R.color.colorLightGray))!!.show()

        val sbView = snackbar!!.view
        val textView = sbView
                .findViewById<TextView>(android.support.design.R.id.snackbar_text)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextColor(view.getContext().getResources()
                    .getColor(R.color.colorLightGray, view.getContext().getTheme()))
        } else {
            textView.setTextColor(view.getContext()
                    .getResources().getColor(R.color.colorLightGray))
        }

        snackbar!!.show()
    }

    companion object {
        val instance = ViewSnackBar()
    }
}