package com.crater.android.core.util

interface Mapper<F, T> {
    fun F.map(): T
}