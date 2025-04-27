package io.github.domi04151309.alwayson.actions.alwayson.draw

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import io.github.domi04151309.alwayson.actions.alwayson.AlwaysOnCustomView
import io.github.domi04151309.alwayson.helpers.P
import java.text.SimpleDateFormat
import java.util.Calendar

object Clock {
    private const val HOURS_ON_ANALOG_CLOCK: Int = 12
    private const val MINUTES_PER_HOUR_ANGLE: Int = 5
    private const val ANALOG_CLOCK_STROKE_WIDTH: Float = 4f
    private const val CIRCULAR_CLOCK_RADIUS: Float = 150f
    private const val CIRCULAR_CLOCK_STROKE_WIDTH: Float = 8f
    private const val CIRCULAR_CLOCK_ANGLE_OFFSET: Float = -90f

    internal fun draw(
        canvas: Canvas,
        utils: Utils,
        flags: BooleanArray,
        timeFormat: SimpleDateFormat,
    ) {
        if (flags.getOrNull(AlwaysOnCustomView.FLAG_CIRCULAR_CLOCK) == true) {
            // Draw the circular clock style
            utils.viewHeight += utils.padding2
            
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            
            // Draw hour in large text
            utils.paint.color = utils.prefs.get(P.DISPLAY_COLOR_CLOCK, P.DISPLAY_COLOR_CLOCK_DEFAULT)
            utils.paint.style = Paint.Style.FILL
            
            val hourText = if (utils.prefs.get(P.USE_12_HOUR_CLOCK, P.USE_12_HOUR_CLOCK_DEFAULT)) {
                (hour % 12).let { if (it == 0) 12 else it }.toString()
            } else {
                hour.toString()
            }
            
            utils.drawRelativeText(
                canvas,
                hourText,
                utils.padding16,
                utils.padding2,
                utils.getPaint(
                    utils.bigTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_CLOCK, P.DISPLAY_COLOR_CLOCK_DEFAULT)
                ),
                0
            )
            
            // Draw minute in smaller text below
            utils.drawRelativeText(
                canvas,
                minute.toString().padStart(2, '0'),
                utils.padding16,
                utils.getTextHeight(utils.bigTextSize).toInt(), // Convert to Int
                utils.getPaint(
                    utils.mediumTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_CLOCK, P.DISPLAY_COLOR_CLOCK_DEFAULT)
                ),
                0
            )
            
            utils.viewHeight += utils.getTextHeight(utils.bigTextSize) + utils.getTextHeight(utils.mediumTextSize)
        }
        else if (flags.getOrNull(AlwaysOnCustomView.FLAG_ANALOG_CLOCK) == true) {
            utils.viewHeight += utils.padding2

            utils.paint.color =
                utils.prefs.get(P.DISPLAY_COLOR_CLOCK, P.DISPLAY_COLOR_CLOCK_DEFAULT)
            utils.paint.style = Paint.Style.STROKE
            utils.paint.strokeWidth = utils.dpToPx(ANALOG_CLOCK_STROKE_WIDTH)
            canvas.drawCircle(
                utils.horizontalRelativePoint,
                utils.viewHeight + utils.getTextHeight(utils.bigTextSize),
                utils.getTextHeight(utils.bigTextSize),
                utils.paint,
            )
            utils.paint.style = Paint.Style.FILL

            val calendar = Calendar.getInstance()
            utils.drawHand(
                canvas,
                (calendar[Calendar.HOUR_OF_DAY] % HOURS_ON_ANALOG_CLOCK *
                    MINUTES_PER_HOUR_ANGLE + calendar[Calendar.MINUTE]),
                true,
            )
            utils.drawHand(
                canvas,
                calendar[Calendar.MINUTE],
                false
            )

            utils.viewHeight += 2 * utils.getTextHeight(utils.bigTextSize) + utils.padding16
        } else {
            utils.drawRelativeText(
                canvas,
                timeFormat.format(System.currentTimeMillis()),
                utils.padding16,
                utils.padding2,
                utils.getPaint(
                    utils.bigTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_CLOCK, P.DISPLAY_COLOR_CLOCK_DEFAULT),
                ),
                if (flags.getOrNull(AlwaysOnCustomView.FLAG_SAMSUNG_3) == true) {
                    -utils.paint.measureText(
                        timeFormat.format(System.currentTimeMillis()),
                    ).toInt() / 2 - utils.padding16
                } else {
                    0
                },
            )
        }
    }
}
