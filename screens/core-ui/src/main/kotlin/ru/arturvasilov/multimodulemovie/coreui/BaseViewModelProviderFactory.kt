package ru.arturvasilov.multimodulemovie.coreui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

abstract class BaseViewModelProviderFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.newInstance()
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }
}