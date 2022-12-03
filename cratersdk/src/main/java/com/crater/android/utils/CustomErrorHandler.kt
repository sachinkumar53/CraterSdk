package com.crater.android.utils

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crater.android.model.ErrorHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

open class BaseViewModel : ViewModel() {

    private val _errorMessageChannel: Channel<String> = Channel(Channel.BUFFERED)

    val errorMessageFlow: Flow<String> get() = _errorMessageChannel.receiveAsFlow()

    private fun sendErrorMessage(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _errorMessageChannel.send(message)
        }
    }

    fun onApiError(error: Throwable) {
        Log.e(TAG, "onApiError: ", error)

        viewModelScope.launch(Dispatchers.Default) {
            var errorMessage = FALLBACK_ERROR_MESSAGE

            if (error is HttpException) {
                errorMessage = try {
                    val response = error.response()?.errorBody()?.string()
                    val craterErrorResponse = CraterParsers.gson.fromJson(response, ErrorHandle::class.java)
                    craterErrorResponse.detail ?: FALLBACK_ERROR_MESSAGE
                } catch (e: Exception) {
                    FALLBACK_ERROR_MESSAGE
                }
            }

            sendErrorMessage(errorMessage)
        }
    }

    companion object {
        const val FALLBACK_ERROR_MESSAGE = "Something went wrong, try later!"
        private const val TAG = "CustomErrorHandler"
    }
}