package com.klibre.utils

/**
 * Created by mota on 28/3/2018.
 */

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Slaush on 18/06/2017.
 */

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item: T? = null

    fun bind(t: T) {
        this.item = t
        onBind()
    }

    abstract fun onBind()
}
