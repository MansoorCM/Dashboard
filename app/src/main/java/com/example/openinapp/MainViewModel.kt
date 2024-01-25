package com.example.openinapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.openinapp.data.Dashboard
import com.example.openinapp.network.dashboardApi
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _data: MutableLiveData<Dashboard> = MutableLiveData()
    val data: LiveData<Dashboard>
        get() = _data

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    init {
        getDashboardData()
    }

    fun removeErrorMessage() {
        _errorMessage.value = ""
    }

    private fun getDashboardData() {
        viewModelScope.launch {
            try {
                val res = dashboardApi.retrofitService.getDashboardData(
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
                )
                _data.value = res
            } catch (e: Exception) {
                _errorMessage.value = "Failed to obtain data. Make sure network is stable."
            }
        }
    }
}

// view model factory used to pass parameters while creating the view model.
class MainViewModelFactory() :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}