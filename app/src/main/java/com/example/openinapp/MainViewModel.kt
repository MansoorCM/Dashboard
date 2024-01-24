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
    private val _status: MutableLiveData<Dashboard> = MutableLiveData()
    val status: LiveData<Dashboard>
        get() = _status

    init {
        getDashboardData()
    }

    fun getDashboardData() {
        viewModelScope.launch {
            val res = dashboardApi.retrofitService.getDashboardData(
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI"
            )
            _status.value = res
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