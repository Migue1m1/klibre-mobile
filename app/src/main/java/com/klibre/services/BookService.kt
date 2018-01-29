package com.klibre.services

import android.os.AsyncTask
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.klibre.adapters.SuggestionsAdapter
import android.app.Activity
import android.database.MatrixCursor
import com.klibre.R
import com.klibre.models.Suggestion
import com.wang.avi.AVLoadingIndicatorView
import android.util.Log
import com.android.volley.Response
import org.json.JSONException
import com.android.volley.toolbox.JsonArrayRequest

import com.klibre.utils.Utils.Companion.jsonToCursor
import com.klibre.utils.Utils.Companion.URL_SERVICE_DOMAIN
import com.klibre.utils.Utils.Companion.URL_DOMAIN



/**
 * Created by Miguel on 28/1/2018.
 */

class BookService {

    private var activity: Activity
    private var searchViewAdapter: SuggestionsAdapter
    private var suggestions: MutableList<Suggestion>
    private lateinit var progressLoading: AVLoadingIndicatorView

    constructor(params: HashMap<String, Any>) : super() {
        activity = params.get("handleActivity") as Activity
        searchViewAdapter = params.get("searchViewAdapter") as SuggestionsAdapter
        suggestions = mutableListOf()
    }

    fun execute(param: String) {

        activity.findViewById<AVLoadingIndicatorView>(R.id.avi_loading)

        val requestQueue: RequestQueue = Volley.newRequestQueue(activity.applicationContext)


        val request = JsonArrayRequest(URL_SERVICE_DOMAIN + "?query=" + param + "&limit=10",
                Response.Listener {
                    jsonArray ->

                    Log.e("jnfdk", jsonArray.toString())
                    for (i in 0 until jsonArray.length()) {
                        try {
                            val jsonObject = jsonArray.getJSONObject(i)

                            val url_icon = URL_DOMAIN + jsonObject
                                    .getString("cover").replace(" ", "%20")
                            val text = jsonObject.getString("title")



                            val suggestion = Suggestion(url_icon, text)
                            this.suggestions.add(suggestion)
                            Log.e("url", suggestions.get(i).url_icon)
                            Log.e("text", suggestions.get(i).text)

                        } catch (e: JSONException) {
                            //mEntries.add("Error: " + e.localizedMessage)
                        }

                    }

                    searchViewAdapter.changeCursor(jsonToCursor(suggestions))
                },
                Response.ErrorListener { volleyError ->
                    /*Toast.makeText(this@MainActivity,
                            "Unable to fetch data: " + volleyError.message,
                            Toast.LENGTH_SHORT).show() */})
        requestQueue.add(request)
    }

}