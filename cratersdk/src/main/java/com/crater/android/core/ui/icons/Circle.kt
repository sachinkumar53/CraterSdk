package com.crater.android.core.ui.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector


public val Icons.Outlined.Circle: ImageVector
    get() {
        if (_circle != null) {
            return _circle!!
        }
        _circle = materialIcon(name = "Circle") {
            materialPath {
                moveTo(12.0f, 2.0f)
                curveTo(6.47f, 2.0f, 2.0f, 6.47f, 2.0f, 12.0f)
                curveToRelative(0.0f, 5.53f, 4.47f, 10.0f, 10.0f, 10.0f)
                reflectiveCurveToRelative(10.0f, -4.47f, 10.0f, -10.0f)
                curveTo(22.0f, 6.47f, 17.53f, 2.0f, 12.0f, 2.0f)
                close()
                moveTo(12.0f, 20.0f)
                curveToRelative(-4.42f, 0.0f, -8.0f, -3.58f, -8.0f, -8.0f)
                curveToRelative(0.0f, -4.42f, 3.58f, -8.0f, 8.0f, -8.0f)
                reflectiveCurveToRelative(8.0f, 3.58f, 8.0f, 8.0f)
                curveTo(20.0f, 16.42f, 16.42f, 20.0f, 12.0f, 20.0f)
                close()
            }
        }
        return _circle!!
    }

private var _circle: ImageVector? = null
