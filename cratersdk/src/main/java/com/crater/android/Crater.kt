package com.crater.android

import com.google.crypto.tink.aead.AeadConfig

object Crater {

    fun initialize() {
        AeadConfig.register()
    }
}