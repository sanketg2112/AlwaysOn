package io.github.domi04151309.alwayson.actions.alwayson.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import io.github.domi04151309.alwayson.actions.alwayson.AlwaysOnCustomView
import io.github.domi04151309.alwayson.helpers.P

object ThemeSpecials {
    internal fun drawDivider(
        canvas: Canvas,
        utils: Utils,
        flags: BooleanArray,
        tempHeight: Float,
    ) {
        if (flags.getOrNull(AlwaysOnCustomView.FLAG_SAMSUNG_3) == true && (
                utils.prefs.get(
                    P.SHOW_CLOCK,
                    P.SHOW_CLOCK_DEFAULT,
                ) || utils.prefs.get(P.SHOW_DATE, P.SHOW_DATE_DEFAULT)
            )
        ) {
            canvas.drawRect(
                utils.horizontalRelativePoint - utils.padding2 / 2,
                tempHeight + utils.padding16 * 2,
                utils.horizontalRelativePoint + utils.padding2 / 2,
                utils.viewHeight - utils.padding16,
                Paint().apply {
                    color = utils.prefs.get(P.DISPLAY_COLOR_DATE, P.DISPLAY_COLOR_DATE_DEFAULT)
                    style = Paint.Style.FILL
                },
            )
        }
    }
}
