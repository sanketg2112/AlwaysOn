package io.github.domi04151309.alwayson.actions.alwayson.draw

import android.graphics.Canvas
import io.github.domi04151309.alwayson.actions.alwayson.AlwaysOnCustomView
import io.github.domi04151309.alwayson.helpers.P
import java.text.SimpleDateFormat

object Date {
    internal fun draw(
        canvas: Canvas,
        utils: Utils,
        flags: BooleanArray,
        tempHeight: Float,
        dateFormat: SimpleDateFormat,
    ) {
        if (flags.getOrNull(AlwaysOnCustomView.FLAG_CIRCULAR_CLOCK) == true) {
            // For circular style, draw the date centered below the clock
            utils.drawRelativeText(
                canvas,
                dateFormat.format(System.currentTimeMillis()).run {
                    if (flags.getOrNull(AlwaysOnCustomView.FLAG_CAPS_DATE) == true) {
                        this.uppercase()
                    } else {
                        this
                    }
                },
                utils.padding2,
                utils.padding2,
                utils.getPaint(
                    utils.mediumTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_DATE, P.DISPLAY_COLOR_DATE_DEFAULT)
                ),
                0 // Centered
            )
            
            // Adjust the viewHeight for elements that follow
            utils.viewHeight += utils.getTextHeight(utils.mediumTextSize) + utils.padding16
        }
        else if (flags.getOrNull(AlwaysOnCustomView.FLAG_SAMSUNG_3) == true) {
            utils.viewHeight =
                tempHeight + utils.getVerticalCenter(utils.getPaint(utils.bigTextSize))
            
            utils.drawRelativeText(
                canvas,
                dateFormat.format(System.currentTimeMillis()).run {
                    if (flags.getOrNull(AlwaysOnCustomView.FLAG_CAPS_DATE) == true) {
                        this.uppercase()
                    } else {
                        this
                    }
                },
                utils.padding2,
                utils.padding2,
                utils.getPaint(
                    if (flags.getOrNull(AlwaysOnCustomView.FLAG_BIG_DATE) == true) utils.bigTextSize else utils.mediumTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_DATE, P.DISPLAY_COLOR_DATE_DEFAULT),
                ),
                utils.paint.measureText(
                    dateFormat.format(System.currentTimeMillis()),
                ).toInt() / 2 + utils.padding16
            )
            
            utils.viewHeight =
                tempHeight + utils.getTextHeight(utils.bigTextSize) + utils.padding16
        } else {
            utils.drawRelativeText(
                canvas,
                dateFormat.format(System.currentTimeMillis()).run {
                    if (flags.getOrNull(AlwaysOnCustomView.FLAG_CAPS_DATE) == true) {
                        this.uppercase()
                    } else {
                        this
                    }
                },
                utils.padding2,
                utils.padding2,
                utils.getPaint(
                    if (flags.getOrNull(AlwaysOnCustomView.FLAG_BIG_DATE) == true) utils.bigTextSize else utils.mediumTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_DATE, P.DISPLAY_COLOR_DATE_DEFAULT),
                ),
                0
            )
        }
    }
}
