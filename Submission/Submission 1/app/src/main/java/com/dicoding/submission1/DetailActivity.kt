package com.dicoding.submission1

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var nameUser: String
    private lateinit var content: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Title
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = "Detail User"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle Received Data

        val imgReceived: ImageView = findViewById(R.id.image_received)
        val nameReceived: TextView = findViewById(R.id.name_received)
        val objectReceived: TextView = findViewById(R.id.object_received)

        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        val imgDetail = user.imgUser
        nameUser = user.name.toString()

        content = "${user.username.toString()}\n" +
                "${user.company.toString()}\n" +
                "${user.location.toString()}\n" +
                "${user.repository.toString()}\n" +
                "${user.followers.toString()}\n" +
                user.following.toString()

        Glide.with(this).load(imgDetail).into(imgReceived)
        nameReceived.text = nameUser
        objectReceived.text = content
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Handle Share
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    // Handle Share
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.bar_share_button) {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, "Share Via")
                this.type = "text/html"
            }
            startActivity(shareIntent)
        } else {
            return super.onOptionsItemSelected(item)
        }
        return true
    }
}