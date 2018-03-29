package com.klibre.utils

import android.support.v7.widget.RecyclerView
import java.nio.file.Files.size
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.text.method.TextKeyListener.clear
import android.icu.lang.UCharacter.GraphemeClusterBreak.T







/**
 * Created by mota on 28/3/2018.
 */
abstract class BaseRecyclerAdapter<T>() : RecyclerView.Adapter<BaseViewHolder<T>>()
{
    protected var list: MutableList<T>

    override fun getItemCount(): Int {
        return list.size
    }

    init {
        list = ArrayList()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun changeList(items: List<T>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

}