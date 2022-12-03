package com.crater.android.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.CraterIcons

public val CraterIcons.Food: ImageVector
    get() {
        if (_food != null) {
            return _food!!
        }
        _food = Builder(
            name = "Food", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
            viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(13.125f, 13.125f)
                curveTo(13.8152f, 13.125f, 14.375f, 13.8246f, 14.375f, 14.6875f)
                curveTo(14.375f, 15.5504f, 13.8152f, 16.25f, 13.125f, 16.25f)
                horizontalLineTo(2.5f)
                curveTo(1.8098f, 16.25f, 1.25f, 15.5504f, 1.25f, 14.6875f)
                curveTo(1.25f, 13.8246f, 1.8098f, 13.125f, 2.5f, 13.125f)
                moveTo(12.5781f, 16.25f)
                curveTo(12.5781f, 17.6309f, 11.7715f, 18.75f, 10.3906f, 18.75f)
                horizontalLineTo(5.2344f)
                curveTo(3.8535f, 18.75f, 3.0469f, 17.6309f, 3.0469f, 16.25f)
                horizontalLineTo(12.5781f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(9.4141f, 4.375f)
                lineTo(9.7047f, 6.8738f)
                moveTo(13.4375f, 13.125f)
                horizontalLineTo(7.0043f)
                curveTo(6.9215f, 13.125f, 6.8422f, 13.1579f, 6.7836f, 13.2164f)
                lineTo(5.7355f, 14.2645f)
                curveTo(5.721f, 14.279f, 5.7038f, 14.2905f, 5.6848f, 14.2984f)
                curveTo(5.6659f, 14.3062f, 5.6455f, 14.3103f, 5.625f, 14.3103f)
                curveTo(5.6045f, 14.3103f, 5.5841f, 14.3062f, 5.5652f, 14.2984f)
                curveTo(5.5462f, 14.2905f, 5.529f, 14.279f, 5.5145f, 14.2645f)
                lineTo(4.4664f, 13.2164f)
                curveTo(4.4078f, 13.1579f, 4.3285f, 13.125f, 4.2457f, 13.125f)
                horizontalLineTo(2.1875f)
                curveTo(1.9389f, 13.125f, 1.7004f, 13.0262f, 1.5246f, 12.8504f)
                curveTo(1.3488f, 12.6746f, 1.25f, 12.4361f, 1.25f, 12.1875f)
                verticalLineTo(12.1875f)
                curveTo(1.25f, 11.9389f, 1.3488f, 11.7004f, 1.5246f, 11.5246f)
                curveTo(1.7004f, 11.3488f, 1.9389f, 11.25f, 2.1875f, 11.25f)
                horizontalLineTo(13.4375f)
                curveTo(13.6861f, 11.25f, 13.9246f, 11.3488f, 14.1004f, 11.5246f)
                curveTo(14.2762f, 11.7004f, 14.375f, 11.9389f, 14.375f, 12.1875f)
                curveTo(14.375f, 12.4361f, 14.2762f, 12.6746f, 14.1004f, 12.8504f)
                curveTo(13.9246f, 13.0262f, 13.6861f, 13.125f, 13.4375f, 13.125f)
                close()
                moveTo(2.5f, 10.7812f)
                verticalLineTo(10.7727f)
                curveTo(2.5f, 8.6242f, 4.2578f, 7.5f, 6.4063f, 7.5f)
                horizontalLineTo(9.2188f)
                curveTo(11.3672f, 7.5f, 13.125f, 8.6328f, 13.125f, 10.7812f)
                verticalLineTo(10.7727f)
                lineTo(2.5f, 10.7812f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.0f, 18.75f)
                horizontalLineTo(15.4418f)
                curveTo(15.7572f, 18.75f, 16.0609f, 18.6308f, 16.292f, 18.4163f)
                curveTo(16.5232f, 18.2018f, 16.6647f, 17.9078f, 16.6883f, 17.5934f)
                lineTo(18.0859f, 4.375f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(14.375f, 4.375f)
                lineTo(15.0f, 1.875f)
                lineTo(16.8359f, 1.25f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(8.75f, 4.375f)
                horizontalLineTo(18.75f)
            }
        }
            .build()
        return _food!!
    }

private var _food: ImageVector? = null
