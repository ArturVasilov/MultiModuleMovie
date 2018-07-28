package ru.arturvasilov.multimodulemovie.coreui

import android.content.Context

interface MainToolsProvider {
    fun provideMainTools(): MainTools
}

fun Context.findMainTools(): MainTools {
    if (applicationContext is MainToolsProvider) {
        return (applicationContext as MainToolsProvider).provideMainTools()
    }
    throw NullPointerException("Failed to find provider for MainToolsProvider")
}

interface MainTools {
    fun provideRouter(): Router
}