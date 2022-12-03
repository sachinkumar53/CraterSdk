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

public val CraterIcons.Calendar: ImageVector
    get() {
        if (_calendar != null) {
            return _calendar!!
        }
        _calendar = Builder(name = "Calendar", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(17.0f, 2.0f)
                horizontalLineTo(15.0f)
                verticalLineTo(1.0f)
                curveTo(15.0f, 0.7348f, 14.8946f, 0.4804f, 14.7071f, 0.2929f)
                curveTo(14.5196f, 0.1054f, 14.2652f, 0.0f, 14.0f, 0.0f)
                curveTo(13.7348f, 0.0f, 13.4804f, 0.1054f, 13.2929f, 0.2929f)
                curveTo(13.1054f, 0.4804f, 13.0f, 0.7348f, 13.0f, 1.0f)
                verticalLineTo(2.0f)
                horizontalLineTo(7.0f)
                verticalLineTo(1.0f)
                curveTo(7.0f, 0.7348f, 6.8946f, 0.4804f, 6.7071f, 0.2929f)
                curveTo(6.5196f, 0.1054f, 6.2652f, 0.0f, 6.0f, 0.0f)
                curveTo(5.7348f, 0.0f, 5.4804f, 0.1054f, 5.2929f, 0.2929f)
                curveTo(5.1054f, 0.4804f, 5.0f, 0.7348f, 5.0f, 1.0f)
                verticalLineTo(2.0f)
                horizontalLineTo(3.0f)
                curveTo(2.2043f, 2.0f, 1.4413f, 2.3161f, 0.8787f, 2.8787f)
                curveTo(0.3161f, 3.4413f, 0.0f, 4.2043f, 0.0f, 5.0f)
                verticalLineTo(17.0f)
                curveTo(0.0f, 17.7956f, 0.3161f, 18.5587f, 0.8787f, 19.1213f)
                curveTo(1.4413f, 19.6839f, 2.2043f, 20.0f, 3.0f, 20.0f)
                horizontalLineTo(17.0f)
                curveTo(17.7956f, 20.0f, 18.5587f, 19.6839f, 19.1213f, 19.1213f)
                curveTo(19.6839f, 18.5587f, 20.0f, 17.7956f, 20.0f, 17.0f)
                verticalLineTo(5.0f)
                curveTo(20.0f, 4.2043f, 19.6839f, 3.4413f, 19.1213f, 2.8787f)
                curveTo(18.5587f, 2.3161f, 17.7956f, 2.0f, 17.0f, 2.0f)
                close()
                moveTo(18.0f, 17.0f)
                curveTo(18.0f, 17.2652f, 17.8946f, 17.5196f, 17.7071f, 17.7071f)
                curveTo(17.5196f, 17.8946f, 17.2652f, 18.0f, 17.0f, 18.0f)
                horizontalLineTo(3.0f)
                curveTo(2.7348f, 18.0f, 2.4804f, 17.8946f, 2.2929f, 17.7071f)
                curveTo(2.1054f, 17.5196f, 2.0f, 17.2652f, 2.0f, 17.0f)
                verticalLineTo(10.0f)
                horizontalLineTo(18.0f)
                verticalLineTo(17.0f)
                close()
                moveTo(18.0f, 8.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(5.0f)
                curveTo(2.0f, 4.7348f, 2.1054f, 4.4804f, 2.2929f, 4.2929f)
                curveTo(2.4804f, 4.1054f, 2.7348f, 4.0f, 3.0f, 4.0f)
                horizontalLineTo(5.0f)
                verticalLineTo(5.0f)
                curveTo(5.0f, 5.2652f, 5.1054f, 5.5196f, 5.2929f, 5.7071f)
                curveTo(5.4804f, 5.8946f, 5.7348f, 6.0f, 6.0f, 6.0f)
                curveTo(6.2652f, 6.0f, 6.5196f, 5.8946f, 6.7071f, 5.7071f)
                curveTo(6.8946f, 5.5196f, 7.0f, 5.2652f, 7.0f, 5.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(13.0f)
                verticalLineTo(5.0f)
                curveTo(13.0f, 5.2652f, 13.1054f, 5.5196f, 13.2929f, 5.7071f)
                curveTo(13.4804f, 5.8946f, 13.7348f, 6.0f, 14.0f, 6.0f)
                curveTo(14.2652f, 6.0f, 14.5196f, 5.8946f, 14.7071f, 5.7071f)
                curveTo(14.8946f, 5.5196f, 15.0f, 5.2652f, 15.0f, 5.0f)
                verticalLineTo(4.0f)
                horizontalLineTo(17.0f)
                curveTo(17.2652f, 4.0f, 17.5196f, 4.1054f, 17.7071f, 4.2929f)
                curveTo(17.8946f, 4.4804f, 18.0f, 4.7348f, 18.0f, 5.0f)
                verticalLineTo(8.0f)
                close()
            }
        }
        .build()
        return _calendar!!
    }

private var _calendar: ImageVector? = null
