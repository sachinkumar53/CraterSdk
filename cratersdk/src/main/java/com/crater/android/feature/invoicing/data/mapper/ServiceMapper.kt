package com.crater.android.feature.invoicing.data.mapper

import com.crater.android.data.dto.invoice.ServiceDto
import com.crater.android.feature.invoicing.data.entity.ServiceEntity
import com.crater.android.feature.invoicing.domain.model.Service


fun Service.toEntity(): ServiceEntity {
    return ServiceEntity(
        name = name,
        price = price,
        SAC = sac
    )
}

fun ServiceEntity.toModel(): Service {
    return Service(
        name = name,
        price = price,
        sac = SAC ?: "",
        quantity = 1,
    )
}

fun Service.toDto(): ServiceDto {
    return ServiceDto(
        SAC = sac,
        name = name,
        notes = null,
        price = price,
        quantity = quantity.toString(),
        total = "",
        amount = ""
    )
}

fun ServiceDto.toService(): Service {
    return Service(
        name = name ?: "",
        price = price ?: "",
        quantity = quantity?.toIntOrNull() ?: 0,
        sac = SAC ?: ""
    )
}