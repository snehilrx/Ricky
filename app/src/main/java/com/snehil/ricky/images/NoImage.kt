package com.snehil.ricky.images

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val NoImage: ImageVector
    get() {
        if (imageVector != null) {
            return imageVector!!
        }
        imageVector = Builder(
            name = "No Image Found", defaultWidth = 80.0.dp, defaultHeight = 80.0.dp,
            viewportWidth = 80.0f, viewportHeight = 80.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFDADCE0)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.396f, 0.076f)
                lineTo(64.423f, 0.076f)
                arcTo(15.32f, 15.32f, 0.0f, false, true, 79.743f, 15.396f)
                lineTo(79.743f, 64.7f)
                arcTo(15.32f, 15.32f, 0.0f, false, true, 64.423f, 80.02f)
                lineTo(15.396f, 80.02f)
                arcTo(15.32f, 15.32f, 0.0f, false, true, 0.076f, 64.7f)
                lineTo(0.076f, 15.396f)
                arcTo(15.32f, 15.32f, 0.0f, false, true, 15.396f, 0.076f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF9AA0A6)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(38.3233f, 14.6173f)
                quadTo(39.3442f, 12.9385f, 40.2982f, 14.5948f)
                lineTo(51.4538f, 33.973f)
                quadTo(52.4077f, 35.6292f, 50.4339f, 35.6517f)
                lineTo(27.3364f, 35.9148f)
                quadTo(25.3625f, 35.9373f, 26.3834f, 34.2585f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF9AA0A6)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(44.76f, 55.518f)
                arcToRelative(11.578f, 11.522f, 0.0f, true, false, 23.156f, 0.0f)
                arcToRelative(11.578f, 11.522f, 0.0f, true, false, -23.156f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF9AA0A6)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(13.841f, 45.709f)
                lineTo(32.55f, 45.709f)
                arcTo(0.886f, 0.886f, 0.0f, false, true, 33.436f, 46.595f)
                lineTo(33.436f, 64.543f)
                arcTo(0.886f, 0.886f, 0.0f, false, true, 32.55f, 65.429f)
                lineTo(13.841f, 65.429f)
                arcTo(0.886f, 0.886f, 0.0f, false, true, 12.955f, 64.543f)
                lineTo(12.955f, 46.595f)
                arcTo(0.886f, 0.886f, 0.0f, false, true, 13.841f, 45.709f)
                close()
            }
        }
            .build()
        return imageVector!!
    }

private var imageVector: ImageVector? = null
