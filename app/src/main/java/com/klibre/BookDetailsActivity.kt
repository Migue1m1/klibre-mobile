package com.klibre

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.klibre.databinding.ActivityBookDetailsBinding
import com.klibre.models.Book
import com.klibre.utils.Utils.Companion.goBackActivity

/**
 * Created by Miguel on 29/1/2018.
 */

class BookDetailsActivity : AppCompatActivity() {
    lateinit var bookBinding: ActivityBookDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding -> se enlaza el layout con el activity
        bookBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_details)

        val book = intent.extras.get("book") as Book

        //Binding -> se enlaza el layout con el modelo
        bookBinding.book = book

        initGUI()
    }

    /*** Inicializacion de GUI ***/
    fun initGUI() {

    }

    override fun onBackPressed() {
        goBackActivity(this)
    }
}