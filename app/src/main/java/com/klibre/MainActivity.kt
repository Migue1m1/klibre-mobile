package com.klibre

import android.database.Cursor
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.klibre.adapters.BookAdapter
import com.klibre.adapters.SuggestionsAdapter
import com.klibre.models.Book
import com.klibre.models.Suggestion
import com.klibre.services.BookDetailsService
import com.klibre.utils.Utils
import com.wang.avi.AVLoadingIndicatorView
import com.klibre.services.BookService
import com.klibre.singleton.ViewSnackBar
import com.klibre.utils.BookViewHolder
import org.json.JSONException


class MainActivity : AppCompatActivity(),
                     View.OnClickListener,
                     SearchView.OnQueryTextListener,
                     SearchView.OnSuggestionListener,
                    BookViewHolder.ClickBook{


    private lateinit var searchView: SearchView
    private lateinit var searchViewAdapter: SuggestionsAdapter
    private lateinit var recycler : RecyclerView
    private lateinit var bookAdapter : BookAdapter
    private lateinit var books : MutableList<Book>
    private val viewSnackBar = ViewSnackBar.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGUI()
        this.onQueryTextSubmit("")
    }

    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.search_close_btn -> cancelTask()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)

        books.clear()
        Log.i("MOTA","QUERYING:"+query)
        val request = JsonArrayRequest(
                Utils.URL_SERVICE_DOMAIN + "?query="
                        + query!!.replace("+", "%2B")
                        + "&limit=30",
                Response.Listener {
                    jsonArray ->
                    Log.i("MOTA","RESULT"+jsonArray.toString())
                    for (i in 0 until jsonArray.length()) {
                        try {
                            val jsonObject = jsonArray.getJSONObject(i)

                            val url_icon = Utils.URL_DOMAIN + jsonObject
                                    .getString("cover")
                                    .replace(" ", "%20")
                                    .replace("+", "%2B")
                            val text = jsonObject.getString("title")
                            val authors = jsonObject.getString("authors")

                            val id = jsonObject.getString("id")
                            val book = Book(id,text,authors,"","", url_icon,"","","")

                            books.add(book)


                        } catch (e: JSONException) {
                            Log.i("MOTA","JSONEX"+e.message)
                        }

                    }
                    bookAdapter.changeList(books)
                    Log.i("MOTA","CHANGE LIST!" + books.size)

                },
                Response.ErrorListener { error ->
                    val nResponse = error.networkResponse
                    var msg: String = getResources().getString(R.string.ERR)

                    if (nResponse != null) {
                        when (nResponse.statusCode) {
                            505 -> {
                            }

                            else -> {
                                viewSnackBar.viewSnackBar(findViewById(R.id.searchView), msg)
                            }
                        }
                    }
                    else {
                        msg = getResources().getString(R.string.CANNOT_CONNECT)
                        Utils.hideSoftKeyBoard(this)
                        viewSnackBar.viewSnackBar(findViewById(R.id.searchView), msg)
                    }
                })
        requestQueue.add(request)
        return true
    }

    /***  Evento producido al cambiar el texto del SearchView ***/
    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.isNotEmpty() && newText.length > 0) {
            val params = HashMap<String, Any>()
            params.put("handleActivity", this)
            params.put("searchViewAdapter", searchViewAdapter)
            BookService(params).execute(newText)
        }
        return true
    }

    override fun onSuggestionSelect(position: Int): Boolean {
        return true
    }

    /***
     * Evento producido al hacer clic en una sugerencia ***/
    override fun onSuggestionClick(position: Int): Boolean {
        val cursor = searchView.getSuggestionsAdapter().getItem(position) as Cursor
        val book_id = cursor.getString(3) //Se recupera el id del libro
        val suggestion = cursor.getString(2) //sugerencia en el campo de texto
        searchView.setQuery(suggestion, false)
        searchView.clearFocus()

        goToDetails(book_id)

        return true
    }

    /*** Inicializacion de GUI ***/
    private fun initGUI() {
        searchView = findViewById<SearchView>(R.id.searchView)
        recycler = findViewById<RecyclerView>(R.id.recycler)
        initRecyclerView()
        searchView.setIconifiedByDefault(false)
        searchView.setSubmitButtonEnabled(false)
        searchView.setQueryRefinementEnabled(true)
        searchView.setOnQueryTextListener(this)
        searchView.setOnSuggestionListener(this)

        searchViewAdapter = SuggestionsAdapter(this, R.layout.list_search_suggestions,
                                               null, Utils.SUGGESTIONS_COLUMS, null, -1000)

        searchView.setSuggestionsAdapter(searchViewAdapter)
        searchView.findViewById<View>(android.support.v7.appcompat.R.id.search_plate)
                .setBackgroundResource(R.drawable.search_view_rounded_background)
        (searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text))
                .setTextColor(getResources().getColor(R.color.colorPrimary))
        (searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.colorHint))
        (searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text))
                .setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        (searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn))
                .setColorFilter(ContextCompat
                        .getColor(this, R.color.colorVine), PorterDuff.Mode.SRC_IN)
        searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_close_btn)
                .setOnClickListener(this)
        (searchView.findViewById<ImageView>(android.support.v7.appcompat.R.id.search_mag_icon))
                .setImageDrawable(null)
    }

    private fun initRecyclerView() {
        bookAdapter = BookAdapter(this)
        bookAdapter.setHasStableIds(true)

        books = ArrayList()
        recycler.adapter = bookAdapter
        recycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onClick(id: String) {
        goToDetails(id)
    }

    /*** Servicio de detalles de un libro
    @param book_id -> id del libro ***/
    private fun goToDetails(book_id: String) {
        val params = HashMap<String, Any>()
        params.put("handleActivity", this)
        params.put("searchViewAdapter", searchViewAdapter)

        BookDetailsService(params).execute(book_id)
    }



    private fun cancelTask() {
        (searchView.findViewById<EditText>(
                android.support.v7.appcompat.R.id.search_src_text)).setText("")
        (findViewById<AVLoadingIndicatorView>(R.id.avi_loading)).hide()

    }

}
