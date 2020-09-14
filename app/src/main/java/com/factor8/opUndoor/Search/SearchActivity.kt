package com.factor8.opUndoor.Search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.factor8.opUndoor.Network.RestCalls
import com.factor8.opUndoor.Network.Responses.SearchResult
import com.factor8.opUndoor.Network.Responses.SearchResult.UserBean
import com.factor8.opUndoor.R
import com.factor8.opUndoor.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), RestCalls.SearchResultI, SearchAdapter.Interaction {

    private lateinit var searchView: SearchView
    private  lateinit var searchAdapter: SearchAdapter

    private  val TAG = "SearchActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        searchView = findViewById(R.id.searchView)
        initSearchView()
        imageView_back.setOnClickListener {
            finish()
        }
    }

    fun initSearchView(){
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setIconifiedByDefault(false)
        searchView.isSubmitButtonEnabled = true


        // ENTER ON COMPUTER KEYBOARD OR ARROW ON VIRTUAL KEYBOARD
        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText
        searchPlate.setOnEditorActionListener{v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                    || actionId == EditorInfo.IME_ACTION_SEARCH ) {
                val searchQuery = v.text.toString()
                Log.e(TAG, "SearchView: (keyboard or arrow) executing search...: ${searchQuery}")
                getRestResults(searchQuery)
                Utils.hideKeyboard(this)
            }
            true
        }

        // SEARCH BUTTON CLICKED (in toolbar)
        val searchButton = searchView.findViewById(R.id.search_go_btn) as View
        searchButton.setOnClickListener {
            val searchQuery = searchPlate.text.toString()
            getRestResults(searchQuery)
            Log.e(TAG, "SearchView: (button) executing search...: ${searchQuery}")
            Utils.hideKeyboard(this)
        }
    }

    fun getRestResults(query: String){
        progress.visibility = VISIBLE
        val restCalls = RestCalls(this)
        restCalls.search(query)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition( R.anim.none,R.anim.activity_slide_out_bottom );
    }

    override fun response(response: SearchResult?) {
        progress.visibility = GONE
        response?.let {
            if(response.status.equals("1")){
                recyclerView_search_results.apply {
                    layoutManager = LinearLayoutManager(this@SearchActivity)
                    searchAdapter = SearchAdapter(this@SearchActivity, response)

                    adapter = searchAdapter
                }
            }
        }

    }

    override fun errorRequest(response: MutableMap<String, String>?) {
        progress.visibility = GONE
        Log.d(TAG, "errorRequest: Exception: ${response?.let { 
            response.get("exception")
        }}")

        Log.d(TAG, "errorRequest: Volley Error: ${response?.let {
            response.get("VolleyError")
        }}")

    }

    override fun userSelected(user: UserBean?) {
        Toast.makeText(this@SearchActivity,"USer ${user!!.f_name}  ${user!!.id}",Toast.LENGTH_LONG).show()
        val gson = Gson()
        val userdata = gson.toJson(user)
        val i = Intent(this,SearchUserProfileActivity::class.java)
        i.putExtra("USER",userdata)
        startActivity(i)
    }

    override fun companySelected(company: SearchResult.CompanyBean?) {
        Toast.makeText(this@SearchActivity,"Company ${company!!.name}  ${company!!.name}",Toast.LENGTH_LONG).show()
    }
}