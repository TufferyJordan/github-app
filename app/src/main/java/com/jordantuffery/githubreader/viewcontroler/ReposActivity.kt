package com.jordantuffery.githubreader.viewcontroler

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.jordantuffery.githubreader.GithubInterface
import com.jordantuffery.githubreader.R
import com.jordantuffery.githubreader.model.RepoDetails
import kotlinx.android.synthetic.main.activity_repos.*

class ReposActivity : AppCompatActivity(), ReposListener {
    private val github = GithubInterface()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)
        github.requestRepos(intent.extras.getString(MainActivity.EXTRA_REPOS_URL), this)
    }

    override fun onReceiveReposList(reposArray: Array<RepoDetails>) {
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, reposArray.map { it.full_name })
        repo_list_view.adapter = adapter
    }
}

interface ReposListener {
    fun onReceiveReposList(reposArray: Array<RepoDetails>)
}
