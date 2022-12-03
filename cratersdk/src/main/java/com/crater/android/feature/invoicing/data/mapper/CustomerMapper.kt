package com.crater.android.feature.invoicing.data.mapper

import com.crater.android.feature.invoicing.data.dto.CustomerDto
import com.crater.android.feature.invoicing.data.entity.CustomerEntity
import com.crater.android.feature.invoicing.domain.model.Customer

internal fun Customer.toEntity(): CustomerEntity {
    return CustomerEntity(
        name = name,
        phone = phone,
        email = emailId.orEmpty(),
        gst = gst,
        city = city,
        pinCode = pinCode,
        panDetails = pan,
        address = address,
        state = state,
        country = country
    )
}

internal fun CustomerDto.toEntity(): CustomerEntity {
    return CustomerEntity(
        name = name,
        phone = phone,
        email = emailId.orEmpty(),
        panDetails = pan,
        gst = gst,
        address = address,
        city = city,
        state = state,
        pinCode = pinCode,
        country = country
    )
}

internal fun Customer.toDto(): CustomerDto {
    return CustomerDto(
        //id,
        name,
        phone,
        emailId,
        gst,
        city,
        pinCode,
        //userId,
        pan,
        address,
        state,
        country
    )
}

internal fun CustomerEntity.toDomain(): Customer {
    return Customer(
        name = name,
        phone = phone,
        emailId = email,
        gst = gst,
        city = city,
        pinCode = pinCode,
        pan = panDetails,
        address = address,
        state = state,
        country = country
    )
}