package com.crater.android.core.data

sealed class Resource<out T : Any>(
    open val data: T?,
    open val throwable: Throwable?
) {

    class Loading : Resource<Nothing>(null, null) {
        override fun toString(): String {
            return "Loading"
        }
    }

    class Success<out T : Any>(override val data: T) : Resource<T>(data, null) {
        override fun toString(): String {
            return data.toString()
        }
    }

    class Error<out T : Any>(override val throwable: Throwable) : Resource<T>(null, throwable) {
        constructor(msg: String) : this(Exception(msg))
        constructor() : this(Exception())

        override fun toString(): String {
            return throwable.toString()
        }
    }
}

inline fun <T : Any, R : Any> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(throwable = throwable)
        is Resource.Loading -> Resource.Loading()
    }
}