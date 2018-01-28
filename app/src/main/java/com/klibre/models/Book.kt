package com.klibre.models

import java.util.*

/**
 * Created by Miguel on 28/1/2018.
 */

class Book {
    var title: String? = null;
    var autor: String? = null;
    var year: Date? = null;
    var isbn: String? = null;

    init {
        title = "";
        autor = "";
        year = null;
        isbn = "";
    }
}