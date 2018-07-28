package ru.arturvasilov.multimodulemovie.api

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @author Artur Vasilov
 */
@Entity
class Movie {

    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String = ""

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    var overview: String = ""

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var title: String = ""

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releasedDate: String = ""

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Double = 0.0

    override fun toString(): String {
        return "Movie{" +
                "id=" + id +
                ", posterPath='" + posterPath + '\''.toString() +
                ", overview='" + overview + '\''.toString() +
                ", title='" + title + '\''.toString() +
                ", releasedDate='" + releasedDate + '\''.toString() +
                ", voteAverage=" + voteAverage +
                '}'.toString()
    }
}
