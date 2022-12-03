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

public val CraterIcons.ArrowDropDown: ImageVector
    get() {
        if (_arrowDropDown != null) {
            return _arrowDropDown!!
        }
        _arrowDropDown = Builder(name = "ArrowDropDown", defaultWidth = 12.0.dp, defaultHeight =
                8.0.dp, viewportWidth = 12.0f, viewportHeight = 8.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.0002f, 1.1697f)
                curveTo(10.8128f, 0.9834f, 10.5594f, 0.8789f, 10.2952f, 0.8789f)
                curveTo(10.031f, 0.8789f, 9.7776f, 0.9834f, 9.5902f, 1.1697f)
                lineTo(6.0002f, 4.7097f)
                lineTo(2.4602f, 1.1697f)
                curveTo(2.2728f, 0.9834f, 2.0194f, 0.8789f, 1.7552f, 0.8789f)
                curveTo(1.491f, 0.8789f, 1.2376f, 0.9834f, 1.0502f, 1.1697f)
                curveTo(0.9565f, 1.2627f, 0.8821f, 1.3733f, 0.8313f, 1.4951f)
                curveTo(0.7805f, 1.617f, 0.7544f, 1.7477f, 0.7544f, 1.8797f)
                curveTo(0.7544f, 2.0117f, 0.7805f, 2.1424f, 0.8313f, 2.2643f)
                curveTo(0.8821f, 2.3861f, 0.9565f, 2.4967f, 1.0502f, 2.5897f)
                lineTo(5.2902f, 6.8297f)
                curveTo(5.3832f, 6.9234f, 5.4938f, 6.9978f, 5.6156f, 7.0486f)
                curveTo(5.7375f, 7.0994f, 5.8682f, 7.1255f, 6.0002f, 7.1255f)
                curveTo(6.1322f, 7.1255f, 6.2629f, 7.0994f, 6.3848f, 7.0486f)
                curveTo(6.5066f, 6.9978f, 6.6172f, 6.9234f, 6.7102f, 6.8297f)
                lineTo(11.0002f, 2.5897f)
                curveTo(11.0939f, 2.4967f, 11.1683f, 2.3861f, 11.2191f, 2.2643f)
                curveTo(11.2699f, 2.1424f, 11.296f, 2.0117f, 11.296f, 1.8797f)
                curveTo(11.296f, 1.7477f, 11.2699f, 1.617f, 11.2191f, 1.4951f)
                curveTo(11.1683f, 1.3733f, 11.0939f, 1.2627f, 11.0002f, 1.1697f)
                close()
            }
        }
        .build()
        return _arrowDropDown!!
    }

private var _arrowDropDown: ImageVector? = null
