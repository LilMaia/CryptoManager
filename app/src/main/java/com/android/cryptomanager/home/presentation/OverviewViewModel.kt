package com.android.cryptomanager.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cryptomanager.home.data.repositories.HomeRepository
import kotlinx.coroutines.launch

class OverviewViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        _loading.postValue(true)
        viewModelScope.launch {
            _loading.postValue(false)
        }
    }
}