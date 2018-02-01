package com.klibre.models

import java.io.Serializable
import android.databinding.BaseObservable
import android.databinding.Bindable

import com.klibre.BR

/**
 * Created by Miguel on 28/1/2018.
 */

data class Book(private var _title: String,
                private var _authors: String,
                private var _date: String,
                private var _isbn: String,
                private var _coverURL: String,
                private var _publisher: String,
                private var _description: String,
                private var _downloadLink: String
) : BaseObservable(), Serializable {

    var title: String
        @Bindable get() = _title
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }
    var authors: String
        @Bindable get() = _authors
        set(value) {
            _authors = value
            notifyPropertyChanged(BR.authors)
        }
    var date: String
        @Bindable get() = _date
        set(value) {
            _date = value
            notifyPropertyChanged(BR.date)
        }
    var isbn: String
        @Bindable get() = _isbn
        set(value) {
            _isbn = value
            notifyPropertyChanged(BR.isbn)
        }
    var coverURL: String
        @Bindable get() = _coverURL
        set(value) {
            _coverURL = value
            notifyPropertyChanged(BR.coverURL)
        }
    var publisher: String
        @Bindable get() = _publisher
        set(value) {
            _publisher = value
            notifyPropertyChanged(BR.publisher)
        }
    var description: String
        @Bindable get() = _description
        set(value) {
            _description = value
            notifyPropertyChanged(BR.description)
        }
    var downloadLink: String
        get() = _downloadLink
        set(value) {
            _downloadLink = value
        }
}