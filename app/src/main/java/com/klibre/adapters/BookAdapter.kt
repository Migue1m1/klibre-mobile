package com.klibre.adapters

import android.view.ViewGroup
import com.klibre.models.Book
import com.klibre.utils.BaseRecyclerAdapter
import com.klibre.utils.BaseViewHolder
import android.view.LayoutInflater
import android.databinding.DataBindingUtil
import android.view.View
import com.klibre.R
import com.klibre.databinding.BookItemBinding
import com.klibre.utils.BookViewHolder


/**
 * Created by mota on 28/3/2018.
 */
class BookAdapter(var click : BookViewHolder.ClickBook) : BaseRecyclerAdapter<Book>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Book> {
        val binding = DataBindingUtil.inflate<BookItemBinding>(LayoutInflater.from(parent!!.getContext()),
                R.layout.book_item, parent, false)
        return BookViewHolder(binding, click)
    }
}