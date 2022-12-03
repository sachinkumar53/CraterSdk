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

val CraterIcons.Filter: ImageVector
    get() {
        if (_filter != null) {
            return _filter!!
        }
        _filter = Builder(
            name = "Filter", defaultWidth = 20.0.dp, defaultHeight = 20.0.dp,
            viewportWidth = 20.0f, viewportHeight = 20.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.0f, 0.0f)
                horizontalLineTo(3.0f)
                curveTo(2.2043f, 0.0f, 1.4413f, 0.3161f, 0.8787f, 0.8787f)
                curveTo(0.3161f, 1.4413f, 0.0f, 2.2043f, 0.0f, 3.0f)
                verticalLineTo(4.17f)
                curveTo(-1.0E-4f, 4.5829f, 0.085f, 4.9915f, 0.25f, 5.37f)
                verticalLineTo(5.43f)
                curveTo(0.3913f, 5.751f, 0.5914f, 6.0427f, 0.84f, 6.29f)
                lineTo(7.0f, 12.41f)
                verticalLineTo(19.0f)
                curveTo(6.9997f, 19.1699f, 7.0426f, 19.3372f, 7.1249f, 19.4859f)
                curveTo(7.2071f, 19.6346f, 7.3259f, 19.7599f, 7.47f, 19.85f)
                curveTo(7.6291f, 19.9486f, 7.8128f, 20.0006f, 8.0f, 20.0f)
                curveTo(8.1565f, 19.9991f, 8.3107f, 19.9614f, 8.45f, 19.89f)
                lineTo(12.45f, 17.89f)
                curveTo(12.6149f, 17.8069f, 12.7536f, 17.6798f, 12.8507f, 17.5227f)
                curveTo(12.9478f, 17.3656f, 12.9994f, 17.1847f, 13.0f, 17.0f)
                verticalLineTo(12.41f)
                lineTo(19.12f, 6.29f)
                curveTo(19.3686f, 6.0427f, 19.5687f, 5.751f, 19.71f, 5.43f)
                verticalLineTo(5.37f)
                curveTo(19.8888f, 4.9944f, 19.9876f, 4.5858f, 20.0f, 4.17f)
                verticalLineTo(3.0f)
                curveTo(20.0f, 2.2043f, 19.6839f, 1.4413f, 19.1213f, 0.8787f)
                curveTo(18.5587f, 0.3161f, 17.7956f, 0.0f, 17.0f, 0.0f)
                close()
                moveTo(11.29f, 11.29f)
                curveTo(11.1973f, 11.3834f, 11.124f, 11.4943f, 11.0742f, 11.6161f)
                curveTo(11.0245f, 11.7379f, 10.9992f, 11.8684f, 11.0f, 12.0f)
                verticalLineTo(16.38f)
                lineTo(9.0f, 17.38f)
                verticalLineTo(12.0f)
                curveTo(9.0008f, 11.8684f, 8.9755f, 11.7379f, 8.9258f, 11.6161f)
                curveTo(8.876f, 11.4943f, 8.8027f, 11.3834f, 8.71f, 11.29f)
                lineTo(3.41f, 6.0f)
                horizontalLineTo(16.59f)
                lineTo(11.29f, 11.29f)
                close()
                moveTo(18.0f, 4.0f)
                horizontalLineTo(2.0f)
                verticalLineTo(3.0f)
                curveTo(2.0f, 2.7348f, 2.1054f, 2.4804f, 2.2929f, 2.2929f)
                curveTo(2.4804f, 2.1054f, 2.7348f, 2.0f, 3.0f, 2.0f)
                horizontalLineTo(17.0f)
                curveTo(17.2652f, 2.0f, 17.5196f, 2.1054f, 17.7071f, 2.2929f)
                curveTo(17.8946f, 2.4804f, 18.0f, 2.7348f, 18.0f, 3.0f)
                verticalLineTo(4.0f)
                close()
            }
        }
            .build()
        return _filter!!
    }

private var _filter: ImageVector? = null
