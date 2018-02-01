package com.klibre.utils

import android.database.MatrixCursor
import com.klibre.models.Suggestion
import android.content.Context.INPUT_METHOD_SERVICE
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import com.klibre.BookDetailsActivity
import android.content.Intent
import com.klibre.models.Book
import org.json.JSONObject
import com.klibre.MainActivity




/**
 * Created by Miguel on 28/1/2018.
 */

class Utils {
    companion object {
        val SUGGESTIONS_COLUMS: Array<String> = arrayOf("_id", "icon", "suggestion", "book_id")
        val URL_SERVICE_DOMAIN: String = "https://klibre.herokuapp.com/api/books"
        val URL_DOMAIN: String = "https://klibre.herokuapp.com/"

        /*** Sugerencias a Cursor
             El cursor es usado por el adaptador de Sugerencias para mostrar la información ***/
        fun suggestionsToCursor(suggestions: MutableList<Suggestion>) : MatrixCursor {
            val cursor = MatrixCursor(SUGGESTIONS_COLUMS)

            for (i in 0 until suggestions.size) {
                val temp = arrayOf(i.toString(),
                                   suggestions.get(i).url_icon,
                                   suggestions.get(i).text,
                                   suggestions.get(i).book_id)
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

        fun jsonToBook(jsonObject: JSONObject) : Book {
            return Book(
                    jsonObject.getString("title"),
                    if (jsonObject.has("authors"))
                        jsonObject.getString("authors") else "N/A",
                    if (jsonObject.has("date")) jsonObject.getString("date") else "N/A",
                    if (jsonObject.has("isbn")) jsonObject.getString("isbn") else "N/A",
                    if (jsonObject.has("cover"))
                        URL_DOMAIN + jsonObject
                                .getString("cover")
                                    .replace(" ", "%20")
                         else "N/A",
                    if (jsonObject.has("publisher"))
                        jsonObject.getString("publisher") else "N/A",
                    if (jsonObject.has("description"))
                        jsonObject.getString("description") else "N/A",
                    if (jsonObject.has("donwloadLink"))//en el webservice debe ser download
                        URL_DOMAIN + jsonObject
                                .getString("donwloadLink")
                                    .replace(" ", "%20")
                                        .replace("+", "%2B")
                    else "N/A"
            )
        }

        fun changueActivity(activity: Activity, book: Book) {
            var intent = Intent(activity, BookDetailsActivity::class.java).apply {
                putExtra("book", book)
            }
            activity.startActivity(intent)
            activity.finish()
        }

        fun goBackActivity(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
            activity.finish()
        }
    }
}