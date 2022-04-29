package com.android.cryptomanager.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.cryptomanager.home.data.models.BitcoinUserData
import com.android.cryptomanager.home.data.models.ChilizUserData
import com.android.cryptomanager.home.data.models.CryptoCard
import com.android.cryptomanager.home.data.models.EthereumUserData
import com.android.cryptomanager.home.data.repositories.HomeRepository
import kotlinx.coroutines.launch

class AddViewModel(private val cryptoCard: CryptoCard, private val homeRepository: HomeRepository) :
    ViewModel() {

    private val _currentValueLive = MutableLiveData<Double>()
    val currentValueLive: LiveData<Double> = _currentValueLive

    private val _currentQuantieLive = MutableLiveData<Double>()
    val currentQuantieLive: LiveData<Double> = _currentQuantieLive

    private val _mediumPrice = MutableLiveData<String>()
    val mediumPrice: LiveData<String> = _mediumPrice

    private val _parcialPrice = MutableLiveData<String>()
    val parcialPrice: LiveData<String> = _parcialPrice

    private val _totalPrice = MutableLiveData<String>()
    val totalPrice: LiveData<String> = _totalPrice

    private val _coinPrice = MutableLiveData<String>()
    val coinPrice: LiveData<String> = _coinPrice

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var currentValue: Double = 0.0
    private var currentQuantie: Double = 0.0

    fun calculaPrecoMedio() {
        if (currentQuantie > 0 && currentValue > 0) {
            _mediumPrice.postValue((currentValue / currentQuantie).toString())
        } else {
            _mediumPrice.postValue("0.0")
        }
    }
}
