package com.resistthedevil5947.bible

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import android.os.Parcel
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.content_main.*
import java.io.IOException
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONArray
import java.sql.Timestamp
import java.util.*
import java.util.concurrent.TimeUnit


//import sun.text.normalizer.UTF16.append



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    internal var searchWord: String? = ""
    internal var word: CharSequence = ""

    fun loadJSONFromAsset(filename: String): String {

        var json = ""

        try {
            val `is` = assets.open(filename)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return json
    }

    private fun getCurrentID(): Int {
        var currentID: Int = 0
        val date = Date()
        val time = date.time
        val startDate = java.sql.Timestamp.valueOf("2018-06-23 14:45:00.0")
        val current = Timestamp(date.time)
        val then = startDate.time
        val now = current.time
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now - then) + 1
        currentID = minutes.toInt()
        while (currentID > 31102) {
            currentID = currentID!! - 31102
        }
        return currentID
    }

    //RESTORES THE SEARCHWORD AND RESULTS
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
//        Log.i(TAG, "onRestoreInstanceState")

        val searchkeyword = savedInstanceState?.getString("searchWord")

        var searchView = findViewById(R.id.search_bar) as SearchView
        //resubmits the search key word
        searchView.setQuery(searchkeyword, true)
        searchView.onActionViewExpanded()
        searchView.setQuery(searchkeyword, false)
        searchView.clearFocus()


    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState?.putString("searchWord", searchWord)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val bibleverse = BibleVerse()
        try {
            val obj = JSONObject(loadJSONFromAsset("bible.json"))
            val bibleArray = obj.getJSONArray("bible")
            val verseID = getCurrentID()
            val jsonObject = bibleArray.getJSONObject(verseID)
            val book = jsonObject.getString("book")
            val chapter = jsonObject.getString("chapter")
            val verse = jsonObject.getString("verse")
            val word = jsonObject.getString("word")
            bibleverse.book = book
            bibleverse.chapter = chapter
            bibleverse.verse = verse
            bibleverse.word = word


        } catch (e: JSONException) {
            e.printStackTrace()
        }

        word = bibleverse.book.toString() + bibleverse.chapter.toString() + bibleverse.verse.toString() + bibleverse.word.toString()
        fab.setOnClickListener { view ->
            Snackbar.make(view, word, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        var searchView = findViewById(R.id.search_bar) as SearchView
        var searchButton = findViewById(R.id.search_button) as Button
        val recList = findViewById(R.id.cardList) as RecyclerView
        recList.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recList.layoutManager = llm


        searchButton.setOnClickListener{
            searchView.setQuery(searchWord, true)
        }





        //loads the search engine to search the bible in the sqlite database
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchWord = query
                val ca = VerseAdapter(searchItem(searchWord))
                recList.adapter = ca
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchWord = newText
                return false
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }



    fun showSearch(view: LinearLayout) {
        view.visibility = View.VISIBLE
    }

    fun hideSearch(view: LinearLayout) {
        view.visibility = View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        var searchlayout = findViewById(R.id.search_layout) as LinearLayout

        when (item.itemId) {
            R.id.nav_search -> {

                showSearch(searchlayout)



                // Handle the search action

            }
            R.id.nav_favorites -> {
                hideSearch(search_layout)


            }
            R.id.nav_history -> {
                hideSearch(search_layout)

            }
            R.id.nav_topics -> {
                hideSearch(search_layout)

            }
            R.id.nav_books -> {
                hideSearch(search_layout)

            }
            R.id.nav_lookup -> {
                hideSearch(search_layout)

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun searchItem(keyword: String?): List<VerseInfo> {
        val size = 30
        val searchresults = ArrayList<VerseInfo>()
        for (i in 1..size) {
            val vi = VerseInfo()
            vi.book = VerseInfo.BOOK_PREFIX + i + keyword
            vi.chapter = VerseInfo.CHAPTER_PREFIX + i + keyword
            vi.verse = VerseInfo.VERSE_PREFIX + i + keyword
            vi.word = VerseInfo.WORD_PREFIX + i + keyword

            searchresults.add(vi)

        }

        return searchresults
    }


}
