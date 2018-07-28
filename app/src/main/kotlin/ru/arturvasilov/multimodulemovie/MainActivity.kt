package ru.arturvasilov.multimodulemovie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.arturvasilov.multimodulemoview.list.MovieListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.mainContainer, MovieListFragment())
                    .commit()
        }
    }
}