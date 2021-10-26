package com.example.rundown

import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.cursoradapter.widget.CursorAdapter
import androidx.cursoradapter.widget.SimpleCursorAdapter
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rundown.model.Event
import com.example.rundown.repository.Repository
import com.example.rundown.ui.adapter.EventListAdapter

class MainActivity : AppCompatActivity() {

    val teamMap: HashMap<String, Int> = hashMapOf(

        "Atlanta Falcons" to 134942,
        "Baltimore Ravens" to 134922,
        "Buffalo Bills" to 134918,
        "Carolina Panthers" to 134943,
        "Chicago Bears" to 134938,
        "Cincinnati Bengals" to 134923,
        "Cleveland Browns" to 134924,
        "Dallas Cowboys" to 134934,
        "Denver Broncos" to 134930,
        "Detroit Lions" to 134939,
        "Green Bay Packers" to 134940,
        "Houston Texans" to 134926,
        "Indianapolis Colts" to 134927,
        "Jacksonville Jaguars" to 134928,
        "Kansas City Chiefs" to 134931,
        "Las Vegas Raiders" to 134932,
        "Los Angeles Chargers" to 135908,
        "Los Angeles Rams" to 135907,
        "Miami Dolphins" to 134919,
        "Minnesota Vikings" to 134941,
        "New England Patriots" to 134920,
        "New Orleans Saints" to 134944,
        "New York Giants" to 134935,
        "New York Jets" to 134921,
        "Philadelphia Eagles" to 134936,
        "Pittsburgh Steelers" to 134925,
        "San Francisco 49ers" to 134948,
        "Seattle Seahawks" to 134949,
        "Tampa Bay Buccaneers" to 134945,
        "Tennessee Titans" to 134929,
        "Washington" to 134937

    )

    private lateinit var viewModel: MainViewModel
    private val eventAdapter by lazy { EventListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = eventAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        retrieveData()

    }

    fun retrieveData() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)


        // Accessing SharedPreferences for user's team, defaults to Eagles

        val sharedPreferences = getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        var selectedTeam = sharedPreferences.getInt(
            getString(R.string.saved_team_id_string), 134936)


        // GET request for team name

        var teamName: String
        viewModel.getTeam(selectedTeam)
        viewModel.teamResponse.observe(this, { response ->
            if (response.isSuccessful) {
                teamName = response.body()!!.teams[0].strTeam
                title = teamName
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })


        // GET request for seasons

        viewModel.getSeasons()
        viewModel.seasonsResponse.observe(this, { response ->
            if (response.isSuccessful) {
                val last = response.body()!!.seasons.last()
                val editor = sharedPreferences.edit()
                editor.putString(getString(R.string.current_season_string), last.strSeason)
                editor.apply()
            } else {
                Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
            }
        })
        val currSeason = sharedPreferences.getString(
            getString(R.string.current_season_string), "2021")


        // GET requests for events by round, then filtered by user team

        val relevantEvents: ArrayList<Event> = arrayListOf()
        for (round in 1..18) {
            if (currSeason != null) {

                viewModel.getEvents(4391, round, currSeason)
                viewModel.eventsResponse.observe(this, { response ->
                    if (response.isSuccessful) {
                        // iterate over response and add relevant events
                        response.body()!!.events.forEach {
                            if (it.idAwayTeam == selectedTeam || it.idHomeTeam == selectedTeam) {
                                if (!relevantEvents.contains(it)){
                                    relevantEvents.add(it)
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, response.code(), Toast.LENGTH_SHORT).show()
                    }
                })

            }
        }

        // sending to recyclerview
        relevantEvents.sortedBy { (it.dateEvent.replace("-", "")).toInt() }
        eventAdapter.setData(relevantEvents)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem: MenuItem? = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.queryHint = getString(R.string.search)
        searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).threshold = 1

        val from = arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1)
        val to = intArrayOf(R.id.item_label)
        val cursorAdapter = SimpleCursorAdapter(
            this, R.layout.search_item, null, from,
            to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        val suggestions = teamMap.keys

        searchView.suggestionsAdapter = cursorAdapter


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query in teamMap.keys && query != null) {
                    // Adding new selected team to SharedPreferences
                    val sharedPref = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                    val editor = sharedPref?.edit()
                    editor?.putInt(getString(R.string.saved_team_id_string), teamMap[query]!!)
                    editor?.apply()
                    searchView.clearFocus()
                    retrieveData()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val cursor = MatrixCursor(arrayOf(BaseColumns._ID, SearchManager.SUGGEST_COLUMN_TEXT_1))
                query?.let {
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(query, true))
                            cursor.addRow(arrayOf(index, suggestion))
                    }
                }
                cursorAdapter.changeCursor(cursor)
                return true
            }

        })


        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {

            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = searchView.suggestionsAdapter.getItem(position) as Cursor
                val selection = cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                searchView.setQuery(selection, true)
                return true
            }

        })

        return true
    }
}