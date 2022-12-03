package com.crater.android.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.CraterIcons

public val CraterIcons.Car: ImageVector
    get() {
        if (_car != null) {
            return _car!!
        }
        _car = Builder(
            name = "Car", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp, viewportWidth
            = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(18.3479f, 9.1641f)
                curveTo(18.0616f, 8.784f, 16.9979f, 8.5223f, 16.5479f, 7.8383f)
                curveTo(16.0979f, 7.1543f, 15.7296f, 5.673f, 14.5843f, 5.1039f)
                curveTo(13.439f, 4.5348f, 11.2499f, 4.375f, 9.9999f, 4.375f)
                curveTo(8.7499f, 4.375f, 6.5624f, 4.5313f, 5.4155f, 5.1027f)
                curveTo(4.2687f, 5.6742f, 3.9019f, 7.1543f, 3.4519f, 7.8371f)
                curveTo(3.0019f, 8.5199f, 1.9382f, 8.784f, 1.6519f, 9.1641f)
                curveTo(1.3655f, 9.5441f, 1.164f, 11.9469f, 1.2866f, 13.125f)
                curveTo(1.4093f, 14.3031f, 1.6382f, 15.0f, 1.6382f, 15.0f)
                horizontalLineTo(4.9976f)
                curveTo(5.5476f, 15.0f, 5.7265f, 14.7934f, 6.8515f, 14.6875f)
                curveTo(8.0858f, 14.5703f, 9.2968f, 14.5312f, 9.9999f, 14.5312f)
                curveTo(10.703f, 14.5312f, 11.953f, 14.5703f, 13.1866f, 14.6875f)
                curveTo(14.3116f, 14.7941f, 14.4964f, 15.0f, 15.0405f, 15.0f)
                horizontalLineTo(18.3608f)
                curveTo(18.3608f, 15.0f, 18.5897f, 14.3031f, 18.7124f, 13.125f)
                curveTo(18.8351f, 11.9469f, 18.6327f, 9.5441f, 18.3479f, 9.1641f)
                close()
                moveTo(15.6249f, 15.0f)
                horizontalLineTo(17.8124f)
                verticalLineTo(15.625f)
                horizontalLineTo(15.6249f)
                verticalLineTo(15.0f)
                close()
                moveTo(2.1874f, 15.0f)
                horizontalLineTo(4.3749f)
                verticalLineTo(15.625f)
                horizontalLineTo(2.1874f)
                verticalLineTo(15.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF0D0D0D)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.2372f, 12.0782f)
                curveTo(14.0063f, 11.8114f, 13.254f, 11.5887f, 12.2579f, 11.4395f)
                curveTo(11.2618f, 11.2903f, 10.8985f, 11.2516f, 10.0079f, 11.2516f)
                curveTo(9.1173f, 11.2516f, 8.7122f, 11.3157f, 7.7575f, 11.4395f)
                curveTo(6.8028f, 11.5633f, 6.086f, 11.7836f, 5.7786f, 12.0782f)
                curveTo(5.3173f, 12.525f, 5.9931f, 13.0266f, 6.5235f, 13.0875f)
                curveTo(7.0376f, 13.1461f, 8.0653f, 13.1246f, 10.0122f, 13.1246f)
                curveTo(11.9591f, 13.1246f, 12.9868f, 13.1461f, 13.5009f, 13.0875f)
                curveTo(14.0306f, 13.0231f, 14.6579f, 12.5563f, 14.2372f, 12.0782f)
                close()
                moveTo(16.8583f, 9.4957f)
                curveTo(16.8561f, 9.4647f, 16.8425f, 9.4356f, 16.8202f, 9.414f)
                curveTo(16.7978f, 9.3923f, 16.7683f, 9.3797f, 16.7372f, 9.3785f)
                curveTo(16.2759f, 9.3622f, 15.8075f, 9.395f, 14.9767f, 9.6399f)
                curveTo(14.5528f, 9.7636f, 14.1547f, 9.9629f, 13.8017f, 10.2282f)
                curveTo(13.7126f, 10.2977f, 13.7442f, 10.4856f, 13.856f, 10.5055f)
                curveTo(14.5408f, 10.5858f, 15.2297f, 10.6263f, 15.9192f, 10.6266f)
                curveTo(16.3329f, 10.6266f, 16.7599f, 10.5094f, 16.8392f, 10.1407f)
                curveTo(16.8796f, 9.9281f, 16.8861f, 9.7104f, 16.8583f, 9.4957f)
                close()
                moveTo(3.1419f, 9.4957f)
                curveTo(3.1441f, 9.4647f, 3.1577f, 9.4356f, 3.18f, 9.414f)
                curveTo(3.2024f, 9.3923f, 3.2319f, 9.3797f, 3.263f, 9.3785f)
                curveTo(3.7243f, 9.3622f, 4.1927f, 9.395f, 5.0235f, 9.6399f)
                curveTo(5.4474f, 9.7636f, 5.8455f, 9.9629f, 6.1985f, 10.2282f)
                curveTo(6.2876f, 10.2977f, 6.256f, 10.4856f, 6.1442f, 10.5055f)
                curveTo(5.4594f, 10.5858f, 4.7705f, 10.6263f, 4.081f, 10.6266f)
                curveTo(3.6673f, 10.6266f, 3.2403f, 10.5094f, 3.161f, 10.1407f)
                curveTo(3.1206f, 9.9281f, 3.1142f, 9.7104f, 3.1419f, 9.4957f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0D0D0D)),
                strokeLineWidth = 1.25f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(3.0469f, 8.2422f)
                curveTo(3.0469f, 8.2422f, 4.8574f, 7.7734f, 10.0f, 7.7734f)
                curveTo(15.1426f, 7.7734f, 16.9531f, 8.2422f, 16.9531f, 8.2422f)
                moveTo(16.875f, 7.5f)
                horizontalLineTo(17.5f)
                horizontalLineTo(16.875f)
                close()
                moveTo(2.5f, 7.5f)
                horizontalLineTo(3.125f)
                horizontalLineTo(2.5f)
                close()
            }
        }
            .build()
        return _car!!
    }

private var _car: ImageVector? = null
