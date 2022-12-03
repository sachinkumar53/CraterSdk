package com.crater.android.core.extension

import androidx.fragment.app.Fragment

fun Fragment.openActivity(destination: Class<*>) {
    requireContext().openActivity(destination)
}
