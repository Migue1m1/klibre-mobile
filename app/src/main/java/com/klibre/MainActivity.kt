package com.klibre

import android.database.Cursor
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.SearchView
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import com.klibre.adapters.SuggestionsAdapter
import com.klibre.utils.Utils
import android.R.string.cancel
import com.wang.avi.AVLoadingIndicatorView
import com.klibre.R.id.searchView
import com.klibre.services.BookService


class MainActivity : AppCompatActivity(), View.OnClickListener, SearchView.OnQueryTextListener,
                     SearchView.OnSuggestionListener, AdapterView.OnItemSelectedListener {

    private lateinit var searchView: SearchView
    private lateinit var searchViewAdapter : SuggestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initGUI()
    }

    override fun onClick(view: View) {
        when (view.getId()) {
            R.id.search_close_btn -> cancelTask()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

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

    override fun onSuggestionClick(position: Int): Boolean {
        val cursor = searchView.getSuggestionsAdapter().getItem(position) as Cursor
        val suggestion = cursor.getString(2)
        searchView.setQuery(suggestion, false)
        searchView.clearFocus()

        //selectDetailsService(suggestion)

        return true
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initGUI() {
        searchView = findViewById<SearchView>(R.id.searchView)
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
        /*(searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text))
                .setTypeface(font);*/
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

    private fun cancelTask() {
        (searchView.findViewById<EditText>(
                android.support.v7.appcompat.R.id.search_src_text)).setText("")
        (findViewById<AVLoadingIndicatorView>(R.id.avi_loading)).hide()
        /*if (movieService != null)
            movieService.cancel(true)
        if (actorService != null)
            actorService.cancel(true)
        if (directorService != null)
            directorService.cancel(true)*/
    }

}
