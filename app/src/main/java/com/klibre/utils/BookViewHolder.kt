package com.klibre.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.klibre.R
import com.klibre.databinding.BookItemBinding
import com.klibre.models.Book
import kotlinx.android.synthetic.main.activity_book_details.view.*

/**
 * Created by mota on 28/3/2018.
 */
class BookViewHolder(private val binding: BookItemBinding,var click : ClickBook) : BaseViewHolder<Book>(binding.root) {

    override fun onBind() {
        binding.book = item

        binding.root.setOnClickListener {
            Log.i("MOTA","CLICK"+ item?.id)
            click.onClick(this.item!!.id)
        }
    }

    interface ClickBook
    {
        fun onClick(id: String)
    }

}
