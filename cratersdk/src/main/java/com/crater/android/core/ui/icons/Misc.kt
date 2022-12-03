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

public val CraterIcons.Misc: ImageVector
    get() {
        if (_misc != null) {
            return _misc!!
        }
        _misc = Builder(name = "Misc", defaultWidth = 18.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 18.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF0D0D0D)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.666f, 5.9167f)
                verticalLineTo(1.3333f)
                curveTo(0.666f, 1.1123f, 0.7538f, 0.9004f, 0.9101f, 0.7441f)
                curveTo(1.0664f, 0.5878f, 1.2783f, 0.5f, 1.4993f, 0.5f)
                horizontalLineTo(16.4993f)
                curveTo(16.7204f, 0.5f, 16.9323f, 0.5878f, 17.0886f, 0.7441f)
                curveTo(17.2449f, 0.9004f, 17.3327f, 1.1123f, 17.3327f, 1.3333f)
                verticalLineTo(5.9167f)
                curveTo(16.7801f, 5.9167f, 16.2502f, 6.1362f, 15.8595f, 6.5269f)
                curveTo(15.4688f, 6.9176f, 15.2493f, 7.4475f, 15.2493f, 8.0f)
                curveTo(15.2493f, 8.5525f, 15.4688f, 9.0824f, 15.8595f, 9.4731f)
                curveTo(16.2502f, 9.8638f, 16.7801f, 10.0833f, 17.3327f, 10.0833f)
                verticalLineTo(14.6667f)
                curveTo(17.3327f, 14.8877f, 17.2449f, 15.0996f, 17.0886f, 15.2559f)
                curveTo(16.9323f, 15.4122f, 16.7204f, 15.5f, 16.4993f, 15.5f)
                horizontalLineTo(1.4993f)
                curveTo(1.2783f, 15.5f, 1.0664f, 15.4122f, 0.9101f, 15.2559f)
                curveTo(0.7538f, 15.0996f, 0.666f, 14.8877f, 0.666f, 14.6667f)
                verticalLineTo(10.0833f)
                curveTo(1.2185f, 10.0833f, 1.7485f, 9.8638f, 2.1391f, 9.4731f)
                curveTo(2.5299f, 9.0824f, 2.7494f, 8.5525f, 2.7494f, 8.0f)
                curveTo(2.7494f, 7.4475f, 2.5299f, 6.9176f, 2.1391f, 6.5269f)
                curveTo(1.7485f, 6.1362f, 1.2185f, 5.9167f, 0.666f, 5.9167f)
                close()
                moveTo(2.3327f, 4.64f)
                curveTo(2.9587f, 4.9503f, 3.4856f, 5.4292f, 3.8539f, 6.0229f)
                curveTo(4.2223f, 6.6166f, 4.4175f, 7.3013f, 4.4175f, 8.0f)
                curveTo(4.4175f, 8.6987f, 4.2223f, 9.3834f, 3.8539f, 9.9771f)
                curveTo(3.4856f, 10.5708f, 2.9587f, 11.0498f, 2.3327f, 11.36f)
                verticalLineTo(13.8333f)
                horizontalLineTo(15.666f)
                verticalLineTo(11.36f)
                curveTo(15.04f, 11.0498f, 14.5131f, 10.5708f, 14.1448f, 9.9771f)
                curveTo(13.7764f, 9.3834f, 13.5812f, 8.6987f, 13.5812f, 8.0f)
                curveTo(13.5812f, 7.3013f, 13.7764f, 6.6166f, 14.1448f, 6.0229f)
                curveTo(14.5131f, 5.4292f, 15.04f, 4.9503f, 15.666f, 4.64f)
                verticalLineTo(2.1667f)
                horizontalLineTo(2.3327f)
                verticalLineTo(4.64f)
                close()
                moveTo(6.4994f, 5.5f)
                horizontalLineTo(11.4993f)
                verticalLineTo(7.1667f)
                horizontalLineTo(6.4994f)
                verticalLineTo(5.5f)
                close()
                moveTo(6.4994f, 8.8333f)
                horizontalLineTo(11.4993f)
                verticalLineTo(10.5f)
                horizontalLineTo(6.4994f)
                verticalLineTo(8.8333f)
                close()
            }
        }
        .build()
        return _misc!!
    }

private var _misc: ImageVector? = null
