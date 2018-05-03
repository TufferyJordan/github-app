package com.jordantuffery.githubreader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import com.google.gson.Gson
import com.jordantuffery.githubreader.model.RepoDetails
import com.jordantuffery.githubreader.model.User
import com.jordantuffery.githubreader.model.UserDetails
import com.jordantuffery.githubreader.viewcontroler.ReposListener
import com.jordantuffery.githubreader.viewcontroler.UserAvatarListener
import com.jordantuffery.githubreader.viewcontroler.UserDetailsListener
import okhttp3.OkHttpClient
import okhttp3.Request

class GithubInterface {
    private val client = OkHttpClient()

    fun requestUserDetails(user: User, listener: UserDetailsListener) {
        UserDetailsRequest(client, listener).execute(user)
    }

    fun requestAvatarFromUserDetails(url: String, listener: UserAvatarListener) {
        UserAvatarRequest(client, listener).execute(url)
    }

    fun requestRepos(url: String, listener: ReposListener) {
        ReposListRequest(client, listener).execute(url)
    }

    companion object {
        const val GITHUB_ENDPOINT = "https://api.github.com/users/"
    }
}

private class UserDetailsRequest(val client: OkHttpClient,
                                 val listener: UserDetailsListener): AsyncTask<User, Any, UserDetails>() {
    override fun doInBackground(vararg users: User): UserDetails {
        val request = Request.Builder()
                .url(users[0].url)
                .build()
        val response = client.newCall(request).execute()
        return Gson().fromJson(response.body()?.string(), UserDetails::class.java)
    }

    override fun onPostExecute(result: UserDetails) {
        listener.onReceiveUserDetails(result)
    }
}

private class UserAvatarRequest(val client: OkHttpClient,
                                val listener: UserAvatarListener): AsyncTask<String, Any, Bitmap>() {
    override fun doInBackground(vararg urls: String): Bitmap {
        val request = Request.Builder()
                .url(urls[0])
                .build()
        val response = client.newCall(request).execute()
        val bitmapByteArray = response.body()?.bytes()
        return BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArray?.size!!)
    }

    override fun onPostExecute(result: Bitmap) {
        listener.onReceiveUserAvatar(result)
    }
}

private class ReposListRequest(val client: OkHttpClient, val listener: ReposListener): AsyncTask<String, Any, Array<RepoDetails>>() {
    override fun doInBackground(vararg urls: String): Array<RepoDetails> {
        val request = Request.Builder()
                .url(urls[0])
                .build()
        val response = client.newCall(request).execute()
        val stringJson = response.body()?.string()
        return Gson().fromJson(stringJson,Array<RepoDetails>::class.java)
    }
    override fun onPostExecute(result: Array<RepoDetails>) {
        listener.onReceiveReposList(result)
    }

}
