package com.jordantuffery.githubreader.viewcontroler

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jordantuffery.githubreader.R
import kotlinx.android.synthetic.main.activity_blog.*

class BlogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        val extras = intent.extras
        val url = extras.getString(MainActivity.EXTRA_BLOG_URL)
        blog_web_view.loadUrl(url)
    }
}
