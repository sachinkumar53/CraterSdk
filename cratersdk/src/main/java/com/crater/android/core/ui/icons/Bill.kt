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

public val CraterIcons.Bill: ImageVector
    get() {
        if (_bill != null) {
            return _bill!!
        }
        _bill = Builder(name = "Bill", defaultWidth = 16.0.dp, defaultHeight = 18.0.dp,
                viewportWidth = 16.0f, viewportHeight = 18.0f).apply {
            path(fill = SolidColor(Color(0xFF0D0D0D)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(14.6667f, 17.3346f)
                horizontalLineTo(1.3333f)
                curveTo(1.1123f, 17.3346f, 0.9004f, 17.2468f, 0.7441f, 17.0906f)
                curveTo(0.5878f, 16.9343f, 0.5f, 16.7223f, 0.5f, 16.5013f)
                verticalLineTo(1.5013f)
                curveTo(0.5f, 1.2803f, 0.5878f, 1.0683f, 0.7441f, 0.912f)
                curveTo(0.9004f, 0.7558f, 1.1123f, 0.668f, 1.3333f, 0.668f)
                horizontalLineTo(14.6667f)
                curveTo(14.8877f, 0.668f, 15.0996f, 0.7558f, 15.2559f, 0.912f)
                curveTo(15.4122f, 1.0683f, 15.5f, 1.2803f, 15.5f, 1.5013f)
                verticalLineTo(16.5013f)
                curveTo(15.5f, 16.7223f, 15.4122f, 16.9343f, 15.2559f, 17.0906f)
                curveTo(15.0996f, 17.2468f, 14.8877f, 17.3346f, 14.6667f, 17.3346f)
                close()
                moveTo(13.8333f, 15.668f)
                verticalLineTo(2.3346f)
                horizontalLineTo(2.1667f)
                verticalLineTo(15.668f)
                horizontalLineTo(13.8333f)
                close()
                moveTo(4.6667f, 6.5013f)
                horizontalLineTo(11.3333f)
                verticalLineTo(8.168f)
                horizontalLineTo(4.6667f)
                verticalLineTo(6.5013f)
                close()
                moveTo(4.6667f, 9.8346f)
                horizontalLineTo(11.3333f)
                verticalLineTo(11.5013f)
                horizontalLineTo(4.6667f)
                verticalLineTo(9.8346f)
                close()
            }
        }
        .build()
        return _bill!!
    }

private var _bill: ImageVector? = null
