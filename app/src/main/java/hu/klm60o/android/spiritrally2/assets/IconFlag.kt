package hu.klm60o.android.spiritrally2.assets

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val IconFlag: ImageVector
    get() {
        if (_undefined != null) {
            return _undefined!!
        }
        _undefined = ImageVector.Builder(
            name = "Flag_2",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(200f, 880f)
                verticalLineToRelative(-760f)
                horizontalLineToRelative(640f)
                lineToRelative(-80f, 200f)
                lineToRelative(80f, 200f)
                horizontalLineTo(280f)
                verticalLineToRelative(360f)
                close()
                moveToRelative(80f, -440f)
                horizontalLineToRelative(442f)
                lineToRelative(-48f, -120f)
                lineToRelative(48f, -120f)
                horizontalLineTo(280f)
                close()
                moveToRelative(0f, 0f)
                verticalLineToRelative(-240f)
                close()
            }
        }.build()
        return _undefined!!
    }

private var _undefined: ImageVector? = null
