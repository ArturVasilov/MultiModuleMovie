package ru.arturvasilov.multimodulemovie.coreui

import android.os.Parcel
import android.os.Parcelable

data class Movie(val id: Int, val posterPath: String, val overview: String,
                 val title: String, val releasedDate: String, val voteAverage: Double) : Parcelable {

    @Suppress("UNCHECKED_CAST")
    constructor(`in`: Parcel) :
            this(id = `in`.readInt(), posterPath = `in`.readString(), overview = `in`.readString(),
                    title = `in`.readString(), releasedDate = `in`.readString(), voteAverage = `in`.readDouble())

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeInt(id)
        parcel.writeString(posterPath)
        parcel.writeString(overview)
        parcel.writeString(title)
        parcel.writeString(releasedDate)
        parcel.writeDouble(voteAverage)
    }

    companion object {

        @Suppress("unused")
        @JvmField
        val CREATOR: Parcelable.Creator<Movie> = object : Parcelable.Creator<Movie> {

            override fun createFromParcel(parcel: Parcel): Movie {
                return Movie(parcel)
            }

            override fun newArray(size: Int): Array<Movie> {
                return newArray(size)
            }
        }
    }
}

data class Video(val videoId: String,
                 val site: String,
                 val name: String)