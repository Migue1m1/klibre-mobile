package com.klibre.services

import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.klibre.adapters.SuggestionsAdapter
import android.app.Activity
import com.klibre.R
import com.klibre.models.Suggestion
import com.wang.avi.AVLoadingIndicatorView
import android.util.Log
import com.android.volley.Response
import org.json.JSONException
import com.android.volley.toolbox.JsonArrayRequest

import com.klibre.utils.Utils.Companion.suggestionsToCursor
import com.klibre.utils.Utils.Companion.URL_SERVICE_DOMAIN
import com.klibre.utils.Utils.Companion.URL_DOMAIN
import com.klibre.singleton.ViewSnackBar
import com.klibre.utils.Utils.Companion.hideSoftKeyBoard

/**
 * Created by Miguel on 28/1/2018.
 */

class BookService {

    private var activity: Activity
    private var searchViewAdapter: SuggestionsAdapter
    private var suggestions: MutableList<Suggestion>
    private lateinit var progressLoading: AVLoadingIndicatorView
    private val viewSnackBar = ViewSnackBar.instance

    constructor(params: HashMap<String, Any>) : super() {
        activity = params.get("handleActivity") as Activity
        searchViewAdapter = params.get("searchViewAdapter") as SuggestionsAdapter
        suggestions = mutableListOf()
    }

    fun execute(param: String) {

        activity.findViewById<AVLoadingIndicatorView>(R.id.avi_loading)

        val requestQueue: RequestQueue = Volley.newRequestQueue(activity.applicationContext)


        val request = JsonArrayRequest(
                URL_SERVICE_DOMAIN + "?query="
                        + param.replace("+", "%2B")
                        + "&limit=10",
                Response.Listener {
                    jsonArray ->

                    for (i in 0 until jsonArray.length()) {
                        try {
                            val jsonObject = jsonArray.getJSONObject(i)

                            val url_icon = URL_DOMAIN + jsonObject
                                    .getString("cover")
                                        .replace(" ", "%20")
                                            .replace("+", "%2B")
                            val text = jsonObject.getString("title")
                            val id = jsonObject.getString("id")


                            val suggestion = Suggestion(url_icon, text, id)
                            this.suggestions.add(suggestion)
                            Log.e("url", suggestions.get(i).url_icon)
                            Log.e("text", suggestions.get(i).text)
                            Log.e("id", suggestions.get(i).book_id)

                        } catch (e: JSONException) {
                        }

                    }

                    searchViewAdapter.changeCursor(suggestionsToCursor(suggestions))
                },
                Response.ErrorListener { error ->
                    val nResponse = error.networkResponse
                    var msg: String = activity.getResources().getString(R.string.ERR)

                    if (nResponse != null) {
                        when (nResponse.statusCode) {
                            505 -> {
                            }

                            else -> {
                                viewSnackBar.viewSnackBar(activity.findViewById(R.id.searchView), msg)
                            }
                        }
                    }
                    else {
                        msg = activity.getResources().getString(R.string.CANNOT_CONNECT)
                        hideSoftKeyBoard(activity)
                        viewSnackBar.viewSnackBar(activity.findViewById(R.id.searchView), msg)
                    }
                })
        requestQueue.add(request)
    }
}