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

public val CraterIcons.Heart: ImageVector
    get() {
        if (_heart != null) {
            return _heart!!
        }
        _heart = Builder(name = "Heart", defaultWidth = 18.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 18.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF0D0D0D)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(9.0002f, 1.7742f)
                curveTo(10.9577f, 0.0166f, 13.9827f, 0.075f, 15.8685f, 1.9641f)
                curveTo(17.7535f, 3.8542f, 17.8185f, 6.8642f, 16.0652f, 8.8275f)
                lineTo(8.9985f, 15.9042f)
                lineTo(1.9335f, 8.8275f)
                curveTo(0.1802f, 6.8642f, 0.246f, 3.8491f, 2.1302f, 1.9641f)
                curveTo(4.0177f, 0.0775f, 7.0368f, 0.0141f, 9.0002f, 1.7742f)
                close()
                moveTo(14.6885f, 3.1416f)
                curveTo(13.4385f, 1.89f, 11.4219f, 1.8391f, 10.1135f, 3.0141f)
                lineTo(9.001f, 4.0125f)
                lineTo(7.8877f, 3.015f)
                curveTo(6.5752f, 1.8383f, 4.5627f, 1.89f, 3.3094f, 3.1433f)
                curveTo(2.0677f, 4.385f, 2.0052f, 6.3725f, 3.1493f, 7.6858f)
                lineTo(8.9993f, 13.545f)
                lineTo(14.8494f, 7.6866f)
                curveTo(15.9944f, 6.3725f, 15.9319f, 4.3875f, 14.6885f, 3.1416f)
                close()
            }
        }
        .build()
        return _heart!!
    }

private var _heart: ImageVector? = null
