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

public val CraterIcons.Flight: ImageVector
    get() {
        if (_flight != null) {
            return _flight!!
        }
        _flight = Builder(
            name = "Flight", defaultWidth = 20.0.dp, defaultHeight = 18.0.dp,
            viewportWidth = 20.0f, viewportHeight = 18.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(15.9267f, 7.75f)
                curveTo(15.7939f, 7.75f, 15.3489f, 7.7539f, 15.2235f, 7.7617f)
                lineTo(12.6884f, 7.8281f)
                curveTo(12.6752f, 7.8289f, 12.6621f, 7.8261f, 12.6504f, 7.8199f)
                curveTo(12.6388f, 7.8137f, 12.6291f, 7.8043f, 12.6224f, 7.793f)
                lineTo(7.5606f, 1.6391f)
                curveTo(7.53f, 1.5984f, 7.4908f, 1.5649f, 7.4459f, 1.5409f)
                curveTo(7.4009f, 1.5169f, 7.3513f, 1.5029f, 7.3005f, 1.5f)
                horizontalLineTo(6.2501f)
                lineTo(9.1017f, 7.7891f)
                curveTo(9.1085f, 7.8035f, 9.1114f, 7.8194f, 9.1102f, 7.8352f)
                curveTo(9.109f, 7.8511f, 9.1037f, 7.8664f, 9.0948f, 7.8796f)
                curveTo(9.0859f, 7.8928f, 9.0737f, 7.9035f, 9.0594f, 7.9105f)
                curveTo(9.0451f, 7.9176f, 9.0293f, 7.9208f, 9.0134f, 7.9199f)
                lineTo(4.2599f, 7.9902f)
                curveTo(4.2104f, 7.9917f, 4.1613f, 7.9816f, 4.1165f, 7.9605f)
                curveTo(4.0717f, 7.9395f, 4.0325f, 7.9082f, 4.002f, 7.8691f)
                lineTo(2.5567f, 6.1113f)
                curveTo(2.4395f, 5.959f, 2.22f, 5.8769f, 2.029f, 5.8769f)
                horizontalLineTo(1.2923f)
                curveTo(1.2419f, 5.8769f, 1.2493f, 5.9242f, 1.263f, 5.9719f)
                lineTo(2.038f, 8.7617f)
                curveTo(2.0966f, 8.9111f, 2.0966f, 9.0772f, 2.038f, 9.2266f)
                lineTo(1.2622f, 12.0078f)
                curveTo(1.2392f, 12.084f, 1.2419f, 12.125f, 1.3313f, 12.125f)
                horizontalLineTo(2.0313f)
                curveTo(2.3493f, 12.125f, 2.3927f, 12.0836f, 2.5552f, 11.8789f)
                lineTo(4.0278f, 10.0938f)
                curveTo(4.0585f, 10.055f, 4.0978f, 10.0239f, 4.1425f, 10.0029f)
                curveTo(4.1873f, 9.9819f, 4.2362f, 9.9716f, 4.2856f, 9.9727f)
                lineTo(8.9997f, 10.0781f)
                curveTo(9.0169f, 10.0785f, 9.0337f, 10.0831f, 9.0487f, 10.0915f)
                curveTo(9.0637f, 10.0998f, 9.0764f, 10.1117f, 9.0857f, 10.1261f)
                curveTo(9.0951f, 10.1405f, 9.1008f, 10.157f, 9.1024f, 10.1741f)
                curveTo(9.1039f, 10.1912f, 9.1012f, 10.2084f, 9.0946f, 10.2242f)
                lineTo(6.2501f, 16.5f)
                horizontalLineTo(7.2907f)
                curveTo(7.3415f, 16.497f, 7.391f, 16.4831f, 7.4358f, 16.4592f)
                curveTo(7.4807f, 16.4352f, 7.5198f, 16.4018f, 7.5505f, 16.3613f)
                lineTo(12.6228f, 10.2109f)
                curveTo(12.638f, 10.1875f, 12.7009f, 10.1758f, 12.7278f, 10.1758f)
                lineTo(15.2239f, 10.2422f)
                curveTo(15.3528f, 10.25f, 15.7939f, 10.2539f, 15.9271f, 10.2539f)
                curveTo(17.6564f, 10.2539f, 18.7501f, 9.7785f, 18.7501f, 9.0f)
                curveTo(18.7501f, 8.2215f, 17.661f, 7.75f, 15.9267f, 7.75f)
                close()
            }
        }
            .build()
        return _flight!!
    }

private var _flight: ImageVector? = null
