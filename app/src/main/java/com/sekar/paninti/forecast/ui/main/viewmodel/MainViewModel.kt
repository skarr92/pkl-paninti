package com.sekar.paninti.forecast.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sekar.paninti.forecast.data.repository.MainRepository
import com.sekar.paninti.forecast.utils.Resource
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

//    private val _weather = MutableLiveData<String>()
//    val weather = _weather

    fun getForecast() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getForecast()))
//            val responseData = Resource.success(data = mainRepository.getForecast()).toString()
//            val jsonObject = JSONObject(responseData)
//            val desiredWeather = jsonObject.getString("temp")
//            _weather.value = desiredWeather

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

//    fun getDataFromApi() {
//        apiService.getData().enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    val responseData = response.body()?.string()
//                    val jsonObject = JSONObject(responseData)
//                    val desiredData = jsonObject.getString("desired_field")
//
//                    _data.value = desiredData
//                }
//            }
}