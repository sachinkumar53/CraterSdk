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

public val CraterIcons.Gift: ImageVector
    get() {
        if (_gift != null) {
            return _gift!!
        }
        _gift = Builder(name = "Gift", defaultWidth = 20.0.dp, defaultHeight = 17.0.dp,
                viewportWidth = 20.0f, viewportHeight = 17.0f).apply {
            path(fill = SolidColor(Color(0xFF0D0D0D)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(12.5007f, 0.668f)
                curveTo(13.0859f, 0.6679f, 13.6608f, 0.8219f, 14.1677f, 1.1145f)
                curveTo(14.6745f, 1.4071f, 15.0954f, 1.828f, 15.3879f, 2.3349f)
                curveTo(15.6805f, 2.8417f, 15.8345f, 3.4167f, 15.8344f, 4.0019f)
                curveTo(15.8343f, 4.5872f, 15.6801f, 5.1621f, 15.3873f, 5.6688f)
                lineTo(19.1673f, 5.668f)
                verticalLineTo(7.3346f)
                horizontalLineTo(17.5006f)
                verticalLineTo(15.668f)
                curveTo(17.5006f, 15.889f, 17.4129f, 16.1009f, 17.2566f, 16.2572f)
                curveTo(17.1003f, 16.4135f, 16.8883f, 16.5013f, 16.6673f, 16.5013f)
                horizontalLineTo(3.334f)
                curveTo(3.113f, 16.5013f, 2.901f, 16.4135f, 2.7447f, 16.2572f)
                curveTo(2.5884f, 16.1009f, 2.5006f, 15.889f, 2.5006f, 15.668f)
                verticalLineTo(7.3346f)
                horizontalLineTo(0.834f)
                verticalLineTo(5.668f)
                lineTo(4.614f, 5.6688f)
                curveTo(4.2005f, 4.9529f, 4.0679f, 4.1089f, 4.2418f, 3.3006f)
                curveTo(4.4157f, 2.4924f, 4.8838f, 1.7776f, 5.5551f, 1.2951f)
                curveTo(6.2264f, 0.8126f, 7.0531f, 0.5968f, 7.8746f, 0.6895f)
                curveTo(8.6961f, 0.7823f, 9.4538f, 1.1771f, 10.0007f, 1.7971f)
                curveTo(10.3128f, 1.4416f, 10.6974f, 1.1569f, 11.1285f, 0.9622f)
                curveTo(11.5597f, 0.7675f, 12.0275f, 0.6671f, 12.5007f, 0.668f)
                close()
                moveTo(9.1673f, 7.3346f)
                horizontalLineTo(4.1673f)
                verticalLineTo(14.8346f)
                horizontalLineTo(9.1673f)
                verticalLineTo(7.3346f)
                close()
                moveTo(15.834f, 7.3346f)
                horizontalLineTo(10.834f)
                verticalLineTo(14.8346f)
                horizontalLineTo(15.834f)
                verticalLineTo(7.3346f)
                close()
                moveTo(7.5006f, 2.3346f)
                curveTo(7.0682f, 2.3327f, 6.6519f, 2.499f, 6.3397f, 2.7983f)
                curveTo(6.0275f, 3.0976f, 5.8439f, 3.5066f, 5.8277f, 3.9388f)
                curveTo(5.8115f, 4.3709f, 5.9639f, 4.7925f, 6.2527f, 5.1144f)
                curveTo(6.5416f, 5.4363f, 6.9442f, 5.6333f, 7.3756f, 5.6638f)
                lineTo(7.5006f, 5.668f)
                horizontalLineTo(9.1673f)
                verticalLineTo(4.0013f)
                curveTo(9.1673f, 3.603f, 9.0247f, 3.2179f, 8.7653f, 2.9157f)
                curveTo(8.5059f, 2.6135f, 8.1468f, 2.4141f, 7.7531f, 2.3538f)
                lineTo(7.6248f, 2.3388f)
                lineTo(7.5006f, 2.3346f)
                close()
                moveTo(12.5007f, 2.3346f)
                curveTo(12.0802f, 2.3345f, 11.6752f, 2.4933f, 11.3669f, 2.7792f)
                curveTo(11.0585f, 3.0651f, 10.8697f, 3.457f, 10.8382f, 3.8763f)
                lineTo(10.834f, 4.0013f)
                verticalLineTo(5.668f)
                horizontalLineTo(12.5007f)
                curveTo(12.9211f, 5.6681f, 13.3261f, 5.5093f, 13.6344f, 5.2234f)
                curveTo(13.9428f, 4.9375f, 14.1316f, 4.5456f, 14.1632f, 4.1263f)
                lineTo(14.1673f, 4.0013f)
                curveTo(14.1673f, 3.5593f, 13.9917f, 3.1354f, 13.6792f, 2.8228f)
                curveTo(13.3666f, 2.5102f, 12.9427f, 2.3346f, 12.5007f, 2.3346f)
                close()
            }
        }
        .build()
        return _gift!!
    }

private var _gift: ImageVector? = null
