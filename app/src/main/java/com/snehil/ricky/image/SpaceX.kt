package com.snehil.ricky.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
private fun VectorPreview() {
    Image(SpaceX, null)
}

private var _SpaceX: ImageVector? = null

val SpaceX: ImageVector
    get() {
        if (_SpaceX != null) {
            return _SpaceX!!
        }
        _SpaceX = ImageVector.Builder(
            name = "SpacexSvgrepoCom",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(24f, 7.417f)
                curveTo(8.882f, 8.287f, 1.89f, 14.75f, 0.321f, 16.28f)
                lineTo(0f, 16.583f)
                horizontalLineToRelative(2.797f)
                curveTo(10.356f, 9.005f, 21.222f, 7.663f, 24f, 7.417f)
                close()
                moveToRelative(-17.046f, 6.35f)
                curveToRelative(-0.472f, 0.321f, -0.945f, 0.68f, -1.398f, 1.02f)
                lineToRelative(2.457f, 1.796f)
                horizontalLineToRelative(2.778f)
                close()
                moveTo(2.948f, 10.8f)
                horizontalLineTo(0.189f)
                lineToRelative(3.25f, 2.381f)
                curveToRelative(0.473f, -0.321f, 1.02f, -0.661f, 1.512f, -0.945f)
                close()
            }
        }.build()
        return _SpaceX!!
    }

