package com.jordantuffery.githubreader.model

import com.jordantuffery.githubreader.GithubInterface
import java.net.URL

enum class User {
    APPLE, GOOGLE;

    val url = URL(GithubInterface.GITHUB_ENDPOINT + this.name.toLowerCase())
    val short_url = url.host + url.path
}

class UserDetails(val avatar_url: String?,
                  val repos_url: String,
                  val location: String?,
                  val blog: String?,
                  val name: String,
                  val html_url: String)