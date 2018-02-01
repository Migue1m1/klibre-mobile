package com.klibre

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView

import com.klibre.databinding.ActivityBookDetailsBinding
import com.klibre.models.Book
import com.klibre.services.BookDownloadService
import com.klibre.utils.Utils.Companion.goBackActivity
import kotlinx.android.synthetic.main.activity_book_details.view.*

/**
 * Created by Miguel on 29/1/2018.
 */

class BookDetailsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var book: Book
    lateinit var bookBinding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding -> se enlaza el layout con el activity
        bookBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_details)

        book = intent.extras.get("book") as Book

        //Binding -> se enlaza el layout con el modelo
        bookBinding.book = book

        initGUI()
    }

    /*** Inicializacion de GUI ***/
    fun initGUI() {
        findViewById<ImageView>(R.id.iv_download_file).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.iv_download_file -> {
                val params = HashMap<String, Any>()
                params.put("handleActivity", this)

                BookDownloadService(params).execute(book.title, book.downloadLink)
            }
        }
    }

    override fun onBackPressed() {
        goBackActivity(this)
    }
}