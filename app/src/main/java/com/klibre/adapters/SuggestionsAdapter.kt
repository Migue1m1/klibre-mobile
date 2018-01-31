package com.klibre.adapters

/**
 * Created by Miguel on 28/1/2018.
 */

import android.content.Context
import android.database.Cursor
import android.support.v4.widget.SimpleCursorAdapter
import android.view.View

import android.widget.ImageView
import android.widget.TextView

import com.klibre.R
import com.squareup.picasso.Picasso

/**
 * Created by Miguel on 20/09/2017.
 */

class SuggestionsAdapter : SimpleCursorAdapter {

    constructor (context: Context, layout: Int, c: Cursor?,
                                   from: Array<String>,
                                   to: IntArray?, flags: Int) : super(context, layout,
                                                                          c, from, to, flags)

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
    }
}