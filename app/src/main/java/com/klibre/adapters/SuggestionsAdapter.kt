package com.klibre.adapters

/**
 * Created by Miguel on 28/1/2018.
 */

import android.content.Context
import android.database.Cursor
import android.graphics.Typeface
import android.support.v4.widget.SimpleCursorAdapter
import android.util.Log
import android.view.View

import android.widget.ImageView
import android.widget.TextView

import com.klibre.R
import com.klibre.utils.Utils
import com.squareup.picasso.Picasso

/**
 * Created by Miguel on 20/09/2017.
 */

class SuggestionsAdapter : SimpleCursorAdapter {
    var font: Typeface? = null

    constructor (context: Context, layout: Int, c: Cursor?,
                                   from: Array<String>,
                                   to: IntArray?, flags: Int) : super(context, layout,
                                                                          c, from, to, flags) {


         //font = Typeface.createFromAsset(context.getAssets(), Utils.FONTPATH[0]);
     }

    override fun bindView(view: View, context: Context, cursor: Cursor) {
        val imgSuggestion = view.findViewById<ImageView>(R.id.suggestion_icon)
        if (!cursor.getString(1).toString().isEmpty())
            Picasso.with(view.getContext())
                    .load(cursor.getString(1).toString())
                    .error(R.mipmap.ic_not_image)
                    .placeholder(R.mipmap.ic_not_image)
                    .into(imgSuggestion)

        val tvSuggestion = view.findViewById<TextView>(R.id.suggestion_text)
        tvSuggestion.setText(cursor.getString(2))
        tvSuggestion.setTypeface(font)

    }
}