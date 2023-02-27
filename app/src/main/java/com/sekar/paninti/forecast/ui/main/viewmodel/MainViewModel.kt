package com.sekar.paninti.forecast.ui.main.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.liveData
import com.sekar.paninti.forecast.data.repository.MainRepository
import com.sekar.paninti.forecast.utils.Resource
import com.sekar.paninti.forecast.utils.UnitPreference
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val _unitPreference = MutableLiveData<UnitPreference>()
    val unitPreference: LiveData<UnitPreference>
        get() = _unitPreference

    fun updateUnitPreference(unitPreference: UnitPreference) {
        _unitPreference.value = unitPreference
    }

    fun getForecast() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getForecast()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}