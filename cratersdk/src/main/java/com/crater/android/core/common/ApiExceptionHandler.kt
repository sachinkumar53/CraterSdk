package com.crater.android.core.common

import com.crater.android.model.ErrorHandle
import com.crater.android.utils.CraterParsers
import retrofit2.HttpException

object ApiExceptionHandler {

    fun extractErrorMessage(throwable: Throwable): String? {
        return if (throwable is HttpException) {
            try {
                val json = throwable.response()?.errorBody()?.string()
                val craterErrorResponse = CraterParsers.gson.fromJson(json, ErrorHandle::class.java)
                craterErrorResponse.detail
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else null
    }
}