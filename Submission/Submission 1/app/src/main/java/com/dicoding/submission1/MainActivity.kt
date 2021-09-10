package com.dicoding.submission1

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SEARCH_VIEW = "search_view"
    }

    private lateinit var rvUser: RecyclerView
    private lateinit var dataImg: TypedArray
    private lateinit var dataName: Array<String>
    private lateinit var dataUsername: Array<String>
    private lateinit var dataCompany: Array<String>
    private lateinit var dataLocation: Array<String>
    private lateinit var dataRepository: Array<String>
    private lateinit var dataFollowers: Array<String>
    private lateinit var dataFollowing: Array<String>
    private lateinit var searchQuery: String
    private lateinit var searchView: SearchView
    private var users: ArrayList<User> = arrayListOf()
    private var userAdapter = UserAdapter(users)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Title
        val actionbar = supportActionBar
        actionbar!!.title = "Github Users"

        rvUser = findViewById(R.id.rv_user)
        rvUser.setHasFixedSize(true)
        addItem()
        showRecyclerView()

        if (savedInstanceState != null) {
            val searchResults = savedInstanceState.getString(SEARCH_VIEW) as String
            searchQuery = searchResults
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchQuery = searchView.query.toString()
        outState.putString(SEARCH_VIEW, searchQuery)
    }

    private fun showRecyclerView() {
        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.adapter = userAdapter
        userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) = showSelectedUser(data)
        })
    }

    private fun showSelectedUser(data: User) {
        val moveObjectWithIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveObjectWithIntent.putExtra(DetailActivity.EXTRA_USER, data)
        startActivity(moveObjectWithIntent)
    }

    private fun prepare() {
        dataImg = resources.obtainTypedArray(R.array.avatar)
        dataName = resources.getStringArray(R.array.name)
        dataUsername = resources.getStringArray(R.array.username)
        dataCompany = resources.getStringArray(R.array.company)
        dataLocation = resources.getStringArray(R.array.location)
        dataRepository = resources.getStringArray(R.array.repository)
        dataFollowers = resources.getStringArray(R.array.followers)
        dataFollowing = resources.getStringArray(R.array.following)
    }

    private fun addItem(): ArrayList<User> {
        prepare()
        for (position in dataName.indices) {
            val user = User(
                dataImg.getResourceId(position, -1),
                dataName[position],
                dataUsername[position],
                dataCompany[position],
                dataLocation[position],
                dataRepository[position],
                dataFollowers[position],
                dataFollowing[position]
            )
            users.add(user)
        }
        dataImg.recycle()
        return users
    }

    // Handle Search
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.app_bar_search)
        searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search User or Username"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}