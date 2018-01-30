package com.klibre.utils

import android.database.MatrixCursor
import com.klibre.models.Suggestion
import android.content.Context.INPUT_METHOD_SERVICE
import android.app.Activity
import android.view.inputmethod.InputMethodManager


/**
 * Created by Miguel on 28/1/2018.
 */

class Utils {
    companion object {
        val SUGGESTIONS_COLUMS: Array<String> = arrayOf("_id", "icon", "suggestion")
        val URL_SERVICE_DOMAIN: String = "https://klibre.herokuapp.com/api/books"
        val URL_DOMAIN: String = "https://klibre.herokuapp.com/"

        fun jsonToCursor(suggestions: MutableList<Suggestion>) : MatrixCursor {
            val cursor = MatrixCursor(SUGGESTIONS_COLUMS)

            for (i in 0 until suggestions.size) {
                val temp = arrayOf(i.toString(),
                                   suggestions.get(i).url_icon,
                                   suggestions.get(i).text)
                cursor.addRow(temp)
            }

            return cursor
        }

        /*** Ocultar teclado  */
        fun hideSoftKeyBoard(activity: Activity) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

            if (imm!!.isAcceptingText()) {
                imm!!.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            }
        }
    }
}