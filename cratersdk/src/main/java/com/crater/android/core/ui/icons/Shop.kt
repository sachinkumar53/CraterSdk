package com.crater.android.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.CraterIcons

public val CraterIcons.Shop: ImageVector
    get() {
        if (_shop != null) {
            return _shop!!
        }
        _shop = Builder(name = "Shop", defaultWidth = 18.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 18.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                    strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(5.25f, 5.875f)
                verticalLineTo(4.625f)
                curveTo(5.25f, 3.6304f, 5.6451f, 2.6766f, 6.3484f, 1.9734f)
                curveTo(7.0516f, 1.2701f, 8.0054f, 0.875f, 9.0f, 0.875f)
                verticalLineTo(0.875f)
                curveTo(9.9946f, 0.875f, 10.9484f, 1.2701f, 11.6517f, 1.9734f)
                curveTo(12.3549f, 2.6766f, 12.75f, 3.6304f, 12.75f, 4.625f)
                verticalLineTo(5.875f)
                moveTo(2.125f, 5.875f)
                curveTo(1.9592f, 5.875f, 1.8003f, 5.9408f, 1.6831f, 6.0581f)
                curveTo(1.5659f, 6.1753f, 1.5f, 6.3342f, 1.5f, 6.5f)
                verticalLineTo(14.9375f)
                curveTo(1.5f, 16.1187f, 2.5062f, 17.125f, 3.6875f, 17.125f)
                horizontalLineTo(14.3125f)
                curveTo(15.4937f, 17.125f, 16.5f, 16.1676f, 16.5f, 14.9863f)
                verticalLineTo(6.5f)
                curveTo(16.5f, 6.3342f, 16.4342f, 6.1753f, 16.3169f, 6.0581f)
                curveTo(16.1997f, 5.9408f, 16.0408f, 5.875f, 15.875f, 5.875f)
                horizontalLineTo(2.125f)
                close()
            }
        }
        .build()
        return _shop!!
    }

private var _shop: ImageVector? = null
