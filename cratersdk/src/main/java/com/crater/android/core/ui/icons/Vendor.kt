package com.crater.android.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.crater.android.core.ui.CraterIcons

public val CraterIcons.Vendor: ImageVector
    get() {
        if (_vendor != null) {
            return _vendor!!
        }
        _vendor = Builder(name = "Vendor", defaultWidth = 20.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 20.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.5007f, 10.0363f)
                verticalLineTo(15.668f)
                horizontalLineTo(18.334f)
                verticalLineTo(17.3346f)
                horizontalLineTo(1.6673f)
                verticalLineTo(15.668f)
                horizontalLineTo(2.5006f)
                verticalLineTo(10.0363f)
                curveTo(1.9876f, 9.6939f, 1.5671f, 9.2301f, 1.2763f, 8.6861f)
                curveTo(0.9856f, 8.1421f, 0.8336f, 7.5348f, 0.834f, 6.918f)
                curveTo(0.834f, 6.2288f, 1.0207f, 5.5646f, 1.3615f, 4.9988f)
                lineTo(3.6215f, 1.0846f)
                curveTo(3.6946f, 0.958f, 3.7998f, 0.8528f, 3.9265f, 0.7796f)
                curveTo(4.0532f, 0.7065f, 4.1969f, 0.668f, 4.3432f, 0.668f)
                horizontalLineTo(15.659f)
                curveTo(15.8053f, 0.668f, 15.949f, 0.7065f, 16.0756f, 0.7796f)
                curveTo(16.2023f, 0.8528f, 16.3075f, 0.958f, 16.3807f, 1.0846f)
                lineTo(18.6323f, 4.9863f)
                curveTo(19.1295f, 5.812f, 19.2893f, 6.7976f, 19.0785f, 7.7381f)
                curveTo(18.8677f, 8.6786f, 18.3026f, 9.5017f, 17.5007f, 10.0363f)
                close()
                moveTo(15.834f, 10.6446f)
                curveTo(15.261f, 10.7089f, 14.6809f, 10.6401f, 14.1389f, 10.4435f)
                curveTo(13.5969f, 10.2469f, 13.1076f, 9.9279f, 12.709f, 9.5113f)
                curveTo(12.3592f, 9.8772f, 11.9388f, 10.1684f, 11.4733f, 10.3673f)
                curveTo(11.0078f, 10.5663f, 10.5069f, 10.6688f, 10.0007f, 10.6688f)
                curveTo(9.4946f, 10.6691f, 8.9937f, 10.5668f, 8.5282f, 10.3681f)
                curveTo(8.0627f, 10.1695f, 7.6423f, 9.8786f, 7.2923f, 9.513f)
                curveTo(6.8937f, 9.9294f, 6.4043f, 10.2483f, 5.8623f, 10.4447f)
                curveTo(5.3203f, 10.6412f, 4.7402f, 10.7099f, 4.1673f, 10.6455f)
                verticalLineTo(15.668f)
                horizontalLineTo(15.834f)
                verticalLineTo(10.6455f)
                verticalLineTo(10.6446f)
                close()
                moveTo(4.8248f, 2.3346f)
                lineTo(2.7973f, 5.8455f)
                curveTo(2.6003f, 6.3323f, 2.5944f, 6.8755f, 2.7808f, 7.3664f)
                curveTo(2.9671f, 7.8574f, 3.3321f, 8.2599f, 3.8025f, 8.4933f)
                curveTo(4.2729f, 8.7267f, 4.8141f, 8.7739f, 5.3178f, 8.6253f)
                curveTo(5.8215f, 8.4768f, 6.2505f, 8.1435f, 6.519f, 7.6921f)
                curveTo(6.7982f, 6.9946f, 7.7856f, 6.9946f, 8.0656f, 7.6921f)
                curveTo(8.2201f, 8.0792f, 8.487f, 8.411f, 8.8319f, 8.6447f)
                curveTo(9.1769f, 8.8785f, 9.584f, 9.0034f, 10.0007f, 9.0034f)
                curveTo(10.4173f, 9.0034f, 10.8244f, 8.8785f, 11.1694f, 8.6447f)
                curveTo(11.5143f, 8.411f, 11.7812f, 8.0792f, 11.9357f, 7.6921f)
                curveTo(12.2148f, 6.9946f, 13.2023f, 6.9946f, 13.4823f, 7.6921f)
                curveTo(13.5905f, 7.9583f, 13.7525f, 8.1993f, 13.9583f, 8.3999f)
                curveTo(14.164f, 8.6004f, 14.409f, 8.7563f, 14.6779f, 8.8576f)
                curveTo(14.9468f, 8.9589f, 15.2337f, 9.0035f, 15.5207f, 8.9885f)
                curveTo(15.8076f, 8.9736f, 16.0884f, 8.8995f, 16.3453f, 8.7708f)
                curveTo(16.6022f, 8.6422f, 16.8298f, 8.4617f, 17.0136f, 8.2409f)
                curveTo(17.1974f, 8.0201f, 17.3336f, 7.7636f, 17.4135f, 7.4876f)
                curveTo(17.4935f, 7.2116f, 17.5155f, 6.9221f, 17.4781f, 6.6372f)
                curveTo(17.4408f, 6.3523f, 17.3449f, 6.0782f, 17.1965f, 5.8321f)
                lineTo(15.1757f, 2.3346f)
                horizontalLineTo(4.8257f)
                horizontalLineTo(4.8248f)
                close()
            }
        }
        .build()
        return _vendor!!
    }

private var _vendor: ImageVector? = null
