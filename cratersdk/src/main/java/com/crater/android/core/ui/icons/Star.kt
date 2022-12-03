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

public val CraterIcons.Star: ImageVector
    get() {
        if (_star != null) {
            return _star!!
        }
        _star = Builder(name = "Star", defaultWidth = 17.0.dp, defaultHeight = 17.0.dp,
                viewportWidth = 17.0f, viewportHeight = 17.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.6651f, 6.2886f)
                curveTo(10.3037f, 5.9462f, 10.0712f, 5.4898f, 10.0067f, 4.9961f)
                lineTo(9.6709f, 2.427f)
                lineTo(7.3959f, 3.6653f)
                curveTo(6.9585f, 3.9037f, 6.4523f, 3.984f, 5.9626f, 3.8928f)
                lineTo(3.4159f, 3.4178f)
                lineTo(3.8909f, 5.9645f)
                curveTo(3.9821f, 6.4542f, 3.9018f, 6.9604f, 3.6634f, 7.3978f)
                lineTo(2.4251f, 9.6728f)
                lineTo(4.9942f, 10.0086f)
                curveTo(5.4876f, 10.0733f, 5.9437f, 10.3057f, 6.2859f, 10.667f)
                lineTo(8.0676f, 12.5478f)
                lineTo(9.1809f, 10.2086f)
                curveTo(9.3946f, 9.7591f, 9.7566f, 9.3969f, 10.2059f, 9.1828f)
                lineTo(12.5459f, 8.0695f)
                lineTo(10.6651f, 6.2886f)
                close()
                moveTo(10.6859f, 10.9245f)
                lineTo(8.8418f, 14.7995f)
                curveTo(8.8069f, 14.8727f, 8.7547f, 14.9364f, 8.6897f, 14.985f)
                curveTo(8.6247f, 15.0336f, 8.5488f, 15.0656f, 8.4687f, 15.0783f)
                curveTo(8.3885f, 15.0911f, 8.3065f, 15.0841f, 8.2296f, 15.058f)
                curveTo(8.1528f, 15.0319f, 8.0834f, 14.9875f, 8.0276f, 14.9286f)
                lineTo(5.0759f, 11.8128f)
                curveTo(4.9971f, 11.7297f, 4.892f, 11.6761f, 4.7784f, 11.6611f)
                lineTo(0.5226f, 11.1045f)
                curveTo(0.4422f, 11.0939f, 0.3655f, 11.0639f, 0.2993f, 11.0171f)
                curveTo(0.2331f, 10.9703f, 0.1792f, 10.9081f, 0.1424f, 10.8358f)
                curveTo(0.1056f, 10.7635f, 0.0869f, 10.6834f, 0.0879f, 10.6023f)
                curveTo(0.089f, 10.5212f, 0.1097f, 10.4416f, 0.1484f, 10.3703f)
                lineTo(2.2001f, 6.6003f)
                curveTo(2.2546f, 6.4995f, 2.2729f, 6.383f, 2.2517f, 6.2703f)
                lineTo(1.4659f, 2.0511f)
                curveTo(1.451f, 1.9713f, 1.4557f, 1.889f, 1.4798f, 1.8114f)
                curveTo(1.5038f, 1.7338f, 1.5464f, 1.6632f, 1.6039f, 1.6057f)
                curveTo(1.6613f, 1.5483f, 1.7319f, 1.5057f, 1.8095f, 1.4817f)
                curveTo(1.8871f, 1.4576f, 1.9694f, 1.4529f, 2.0492f, 1.4678f)
                lineTo(6.2684f, 2.2536f)
                curveTo(6.3811f, 2.2748f, 6.4976f, 2.2565f, 6.5984f, 2.202f)
                lineTo(10.3684f, 0.1503f)
                curveTo(10.4398f, 0.1116f, 10.5194f, 0.0909f, 10.6006f, 0.0899f)
                curveTo(10.6817f, 0.0889f, 10.7619f, 0.1076f, 10.8342f, 0.1446f)
                curveTo(10.9065f, 0.1815f, 10.9687f, 0.2354f, 11.0154f, 0.3018f)
                curveTo(11.0622f, 0.3681f, 11.0921f, 0.4448f, 11.1026f, 0.5253f)
                lineTo(11.6592f, 4.7803f)
                curveTo(11.6742f, 4.8939f, 11.7278f, 4.999f, 11.8109f, 5.0778f)
                lineTo(14.9267f, 8.0295f)
                curveTo(14.9856f, 8.0853f, 15.03f, 8.1547f, 15.0561f, 8.2315f)
                curveTo(15.0822f, 8.3084f, 15.0892f, 8.3904f, 15.0764f, 8.4706f)
                curveTo(15.0637f, 8.5507f, 15.0317f, 8.6265f, 14.9831f, 8.6916f)
                curveTo(14.9345f, 8.7566f, 14.8708f, 8.8088f, 14.7976f, 8.8436f)
                lineTo(10.9226f, 10.6878f)
                curveTo(10.8188f, 10.7372f, 10.7353f, 10.8207f, 10.6859f, 10.9245f)
                close()
                moveTo(11.3501f, 12.5303f)
                lineTo(12.5284f, 11.352f)
                lineTo(16.0642f, 14.887f)
                lineTo(14.8851f, 16.0661f)
                lineTo(11.3501f, 12.5303f)
                close()
            }
        }
        .build()
        return _star!!
    }

private var _star: ImageVector? = null
