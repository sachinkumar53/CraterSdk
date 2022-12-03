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

public val CraterIcons.Wallet: ImageVector
    get() {
        if (_wallet != null) {
            return _wallet!!
        }
        _wallet = Builder(name = "Wallet", defaultWidth = 18.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 18.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.666f, 3.8333f)
                verticalLineTo(2.1667f)
                horizontalLineTo(2.3327f)
                verticalLineTo(13.8333f)
                horizontalLineTo(15.666f)
                verticalLineTo(12.1667f)
                horizontalLineTo(8.9993f)
                curveTo(8.7783f, 12.1667f, 8.5664f, 12.0789f, 8.4101f, 11.9226f)
                curveTo(8.2538f, 11.7663f, 8.166f, 11.5543f, 8.166f, 11.3333f)
                verticalLineTo(4.6667f)
                curveTo(8.166f, 4.4457f, 8.2538f, 4.2337f, 8.4101f, 4.0774f)
                curveTo(8.5664f, 3.9211f, 8.7783f, 3.8333f, 8.9993f, 3.8333f)
                horizontalLineTo(15.666f)
                close()
                moveTo(1.4993f, 0.5f)
                horizontalLineTo(16.4993f)
                curveTo(16.7204f, 0.5f, 16.9323f, 0.5878f, 17.0886f, 0.7441f)
                curveTo(17.2449f, 0.9004f, 17.3327f, 1.1123f, 17.3327f, 1.3333f)
                verticalLineTo(14.6667f)
                curveTo(17.3327f, 14.8877f, 17.2449f, 15.0996f, 17.0886f, 15.2559f)
                curveTo(16.9323f, 15.4122f, 16.7204f, 15.5f, 16.4993f, 15.5f)
                horizontalLineTo(1.4993f)
                curveTo(1.2783f, 15.5f, 1.0664f, 15.4122f, 0.9101f, 15.2559f)
                curveTo(0.7538f, 15.0996f, 0.666f, 14.8877f, 0.666f, 14.6667f)
                verticalLineTo(1.3333f)
                curveTo(0.666f, 1.1123f, 0.7538f, 0.9004f, 0.9101f, 0.7441f)
                curveTo(1.0664f, 0.5878f, 1.2783f, 0.5f, 1.4993f, 0.5f)
                close()
                moveTo(9.8327f, 5.5f)
                verticalLineTo(10.5f)
                horizontalLineTo(15.666f)
                verticalLineTo(5.5f)
                horizontalLineTo(9.8327f)
                close()
                moveTo(11.4993f, 7.1667f)
                horizontalLineTo(13.9993f)
                verticalLineTo(8.8333f)
                horizontalLineTo(11.4993f)
                verticalLineTo(7.1667f)
                close()
            }
        }
        .build()
        return _wallet!!
    }

private var _wallet: ImageVector? = null
