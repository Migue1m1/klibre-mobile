package com.klibre.services

import android.app.Activity
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.klibre.R
import com.klibre.adapters.SuggestionsAdapter
import com.klibre.models.Book
import com.klibre.singleton.ViewSnackBar
import com.klibre.utils.Utils
import com.klibre.utils.Utils.Companion.changueActivity
import com.klibre.utils.Utils.Companion.jsonToBook
import com.wang.avi.AVLoadingIndicatorView
import org.json.JSONObject

/**
 * Created by Miguel on 30/1/2018.
 */

class BookDetailsService {

    private var activity: Activity
    private var searchViewAdapter: SuggestionsAdapter
    private lateinit var progressLoading: AVLoadingIndicatorView
    private val viewSnackBar = ViewSnackBar.instance

    constructor(params: HashMap<String, Any>) : super() {
        activity = params.get("handleActivity") as Activity
        searchViewAdapter = params.get("searchViewAdapter") as SuggestionsAdapter
    }

    fun execute(param: String) {

        activity.findViewById<AVLoadingIndicatorView>(R.id.avi_loading)
        progressLoading = activity.findViewById(R.id.avi_loading)
        progressLoading.show()

        val requestQueue: RequestQueue = Volley.newRequestQueue(activity.applicationContext)


        val request = JsonObjectRequest(
                Request.Method.GET,
                Utils.URL_SERVICE_DOMAIN + "/" + param,
                null,
                Response.Listener<JSONObject> {
                    response ->

                    Log.e("jnfdk", response.toString())

                    val book: Book = jsonToBook(response)

                    progressLoading.hide()

                    changueActivity(activity, book)

                },
                Response.ErrorListener {
                    error ->
                    /*val nResponse = error.networkResponse
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
                        Utils.hideSoftKeyBoard(activity)
                        viewSnackBar.viewSnackBar(activity.findViewById(R.id.searchView), msg)
                    }*/
                })


        requestQueue.add(request)
    }
}