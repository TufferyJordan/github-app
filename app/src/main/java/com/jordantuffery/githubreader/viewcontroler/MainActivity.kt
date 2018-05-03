package com.jordantuffery.githubreader.viewcontroler

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jordantuffery.githubreader.GithubInterface
import com.jordantuffery.githubreader.R
import com.jordantuffery.githubreader.model.User
import com.jordantuffery.githubreader.model.UserDetails
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserDetailsListener, UserAvatarListener {

    private val github = GithubInterface()
    private val user = User.GOOGLE

    private var userDetails: UserDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        // fetch UI data
        github.requestUserDetails(user, this)

        user_blog_button.setOnClickListener {
            if (userDetails != null) {
                val intent = Intent(this, BlogActivity::class.java).apply {
                    val blogUrl = userDetails?.blog
                    if (blogUrl != null) {
                        putExtra(EXTRA_BLOG_URL, blogUrl)
                    }
                }
                startActivity(intent)
            }
        }

        user_repo_button.setOnClickListener {
            if (userDetails != null) {
                val intent = Intent(this, ReposActivity::class.java).apply {
                    val reposUrl = userDetails?.repos_url
                    if (reposUrl != null) {
                        putExtra(EXTRA_REPOS_URL, reposUrl)
                    }
                }
                startActivity(intent)
            }
        }
    }

    override fun onReceiveUserDetails(userDetails: UserDetails) {
        this.userDetails = userDetails
        if (userDetails.avatar_url != null) {
            github.requestAvatarFromUserDetails(userDetails.avatar_url, this)
        }
        user_name_text.text = userDetails.name
        user_url_text.text = user.short_url
        user_location_text.text = userDetails.location
    }

    override fun onReceiveUserAvatar(avatar: Bitmap) {
        user_avatar_image.setImageBitmap(avatar)
    }

    companion object {
        const val EXTRA_BLOG_URL = "EXTRA_BLOG_URL"
        const val EXTRA_REPOS_URL = "EXTRA_REPOS_URL"
    }
}

interface UserDetailsListener {
    fun onReceiveUserDetails(userDetails: UserDetails)
}

interface UserAvatarListener {
    fun onReceiveUserAvatar(avatar: Bitmap)
}
