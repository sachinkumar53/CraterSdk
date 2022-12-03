package com.crater.android.feature.invoicing.domain.model

import android.graphics.Bitmap

class PaymentInfo(
    val qrCode: Bitmap?,
    val upiLink: String
)
