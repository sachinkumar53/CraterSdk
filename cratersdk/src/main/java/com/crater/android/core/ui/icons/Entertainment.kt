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

public val CraterIcons.Entertainment: ImageVector
    get() {
        if (_entertainment != null) {
            return _entertainment!!
        }
        _entertainment = Builder(name = "Entertainment", defaultWidth = 18.0.dp, defaultHeight =
                16.0.dp, viewportWidth = 18.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF0D0D0D)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.666f, 1.3275f)
                curveTo(0.6675f, 1.1086f, 0.7551f, 0.8992f, 0.9098f, 0.7443f)
                curveTo(1.0645f, 0.5895f, 1.2738f, 0.5017f, 1.4927f, 0.5f)
                horizontalLineTo(16.506f)
                curveTo(16.9627f, 0.5f, 17.3327f, 0.8708f, 17.3327f, 1.3275f)
                verticalLineTo(14.6725f)
                curveTo(17.3312f, 14.8914f, 17.2436f, 15.1008f, 17.0889f, 15.2557f)
                curveTo(16.9342f, 15.4105f, 16.7249f, 15.4983f, 16.506f, 15.5f)
                horizontalLineTo(1.4927f)
                curveTo(1.2734f, 15.4998f, 1.0631f, 15.4125f, 0.9081f, 15.2573f)
                curveTo(0.7531f, 15.1022f, 0.666f, 14.8918f, 0.666f, 14.6725f)
                verticalLineTo(1.3275f)
                close()
                moveTo(2.3327f, 2.1667f)
                verticalLineTo(13.8333f)
                horizontalLineTo(15.666f)
                verticalLineTo(2.1667f)
                horizontalLineTo(2.3327f)
                close()
                moveTo(7.851f, 5.0125f)
                lineTo(11.9168f, 7.7225f)
                curveTo(11.9626f, 7.7529f, 12.0001f, 7.7942f, 12.026f, 7.8426f)
                curveTo(12.0519f, 7.891f, 12.0655f, 7.9451f, 12.0655f, 8.0f)
                curveTo(12.0655f, 8.0549f, 12.0519f, 8.109f, 12.026f, 8.1574f)
                curveTo(12.0001f, 8.2058f, 11.9626f, 8.2471f, 11.9168f, 8.2775f)
                lineTo(7.8502f, 10.9875f)
                curveTo(7.8f, 11.0207f, 7.7418f, 11.0398f, 7.6817f, 11.0426f)
                curveTo(7.6217f, 11.0455f, 7.5619f, 11.032f, 7.5089f, 11.0036f)
                curveTo(7.4558f, 10.9753f, 7.4114f, 10.9331f, 7.3804f, 10.8815f)
                curveTo(7.3494f, 10.83f, 7.3329f, 10.771f, 7.3327f, 10.7108f)
                verticalLineTo(5.2892f)
                curveTo(7.3328f, 5.2289f, 7.3493f, 5.1697f, 7.3803f, 5.1181f)
                curveTo(7.4114f, 5.0664f, 7.4559f, 5.0241f, 7.509f, 4.9957f)
                curveTo(7.5622f, 4.9673f, 7.6221f, 4.9539f, 7.6824f, 4.9569f)
                curveTo(7.7426f, 4.9598f, 7.8009f, 4.979f, 7.851f, 5.0125f)
                close()
            }
        }
        .build()
        return _entertainment!!
    }

private var _entertainment: ImageVector? = null
