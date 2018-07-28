package ru.arturvasilov.multimodulemovie.api

import android.arch.persistence.room.*

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie")
    fun movies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(movies: List<Movie>)

    @Delete
    fun clear(movies: List<Movie>)
}

@Database(entities = [(Movie::class)], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}