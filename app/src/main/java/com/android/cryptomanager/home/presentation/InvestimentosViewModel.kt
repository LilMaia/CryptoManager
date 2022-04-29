package com.android.cryptomanager.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cryptomanager.home.data.repositories.HomeRepository
import kotlinx.coroutines.launch

class InvestimentosViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _bitcoinPrice = MutableLiveData<Double>()
    val bitcoinPrice: LiveData<Double> = _bitcoinPrice

    private val _ethereumPrice = MutableLiveData<Double>()
    val ethereumPrice: LiveData<Double> = _ethereumPrice

    private val _chilizPrice = MutableLiveData<Double>()
    val chilizPrice: LiveData<Double> = _chilizPrice

    private val _bitcoinInvested = MutableLiveData<Double>()
    val bitcoinInvested: LiveData<Double> = _bitcoinInvested

    private val _ethereumInvested = MutableLiveData<Double>()
    val ethereumInvested: LiveData<Double> = _ethereumInvested

    private val _chilizInvested = MutableLiveData<Double>()
    val chilizInvested: LiveData<Double> = _chilizInvested

    private val _totalInvested = MutableLiveData<Double>()
    val totalInvested: LiveData<Double> = _totalInvested

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

}