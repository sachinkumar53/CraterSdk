package com.crater.android.core.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector


public val Icons.Filled.Cancel: ImageVector
    get() {
        if (_cancel != null) {
            return _cancel!!
        }
        _cancel = materialIcon(name = "Filled.Cancel") {
            materialPath {
                moveTo(12.0f, 2.0f)
                curveTo(6.47f, 2.0f, 2.0f, 6.47f, 2.0f, 12.0f)
                reflectiveCurveToRelative(4.47f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.47f, 10.0f, -10.0f)
                reflectiveCurveTo(17.53f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(17.0f, 15.59f)
                lineTo(15.59f, 17.0f)
                lineTo(12.0f, 13.41f)
                lineTo(8.41f, 17.0f)
                lineTo(7.0f, 15.59f)
                lineTo(10.59f, 12.0f)
                lineTo(7.0f, 8.41f)
                lineTo(8.41f, 7.0f)
                lineTo(12.0f, 10.59f)
                lineTo(15.59f, 7.0f)
                lineTo(17.0f, 8.41f)
                lineTo(13.41f, 12.0f)
                lineTo(17.0f, 15.59f)
                close()
            }
        }
        return _cancel!!
    }

private var _cancel: ImageVector? = null
