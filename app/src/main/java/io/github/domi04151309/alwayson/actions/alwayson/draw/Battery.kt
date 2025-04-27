package io.github.domi04151309.alwayson.actions.alwayson.draw

import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.res.ResourcesCompat
import io.github.domi04151309.alwayson.R
import io.github.domi04151309.alwayson.actions.alwayson.AlwaysOnCustomView
import io.github.domi04151309.alwayson.helpers.P

object Battery {
    private const val PERCENT = "%"

    internal fun drawIconAndPercentage(
        canvas: Canvas,
        utils: Utils,
        batteryIcon: Int,
        batteryLevel: Int,
        batteryIsCharging: Boolean,
        flags: BooleanArray = booleanArrayOf(),
    ) {
        // Special handling for circular clock style
        if (flags.getOrNull(AlwaysOnCustomView.FLAG_CIRCULAR_CLOCK) == true) {
            
            // For circular style, position the battery info below the date
            utils.viewHeight += utils.padding16
            
            utils.drawRelativeText(
                canvas,
                batteryLevel.toString() + PERCENT,
                utils.padding16,
                utils.padding16,
                utils.getPaint(
                    utils.smallTextSize,
                    if (batteryIsCharging) {
                        ResourcesCompat.getColor(utils.resources, R.color.charging, null)
                    } else {
                        utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT)
                    }
                ),
                0 // Centered
            )
            
            // No need to adjust viewHeight here as we're using the existing padding
        } else {
            utils.drawVector(
                canvas,
                batteryIcon,
                (
                    utils.horizontalRelativePoint +
                        utils.getPaint(utils.mediumTextSize).measureText(batteryLevel.toString() + PERCENT).run {
                            if (utils.paint.textAlign == Paint.Align.LEFT) {
                                this
                            } else {
                                this / 2
                            }
                        }
                ).toInt(),
                (
                    utils.viewHeight + utils.padding16 +
                        utils.getVerticalCenter(
                            utils.getPaint(utils.mediumTextSize),
                        )
                ).toInt(),
                if (batteryIsCharging) {
                    ResourcesCompat.getColor(utils.resources, R.color.charging, null)
                } else {
                    utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT)
                },
            )
            utils.drawRelativeText(
                canvas,
                batteryLevel.toString() + PERCENT,
                utils.padding16,
                utils.padding16,
                utils.getPaint(
                    utils.mediumTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT),
                ),
                if (utils.paint.textAlign == Paint.Align.LEFT) 0 else -utils.drawableSize / 2,
            )
        }
    }

    internal fun drawIcon(
        canvas: Canvas,
        utils: Utils,
        batteryIcon: Int,
        batteryIsCharging: Boolean,
        flags: BooleanArray,
    ) {
        // Remove the potentially problematic flags.size check and just ensure the flag index exists
        if (flags.getOrNull(AlwaysOnCustomView.FLAG_CIRCULAR_CLOCK) == true) {
            
            utils.drawVector(
                canvas,
                batteryIcon,
                utils.horizontalRelativePoint.toInt(),
                (utils.viewHeight + utils.padding16).toInt(),
                if (batteryIsCharging) {
                    ResourcesCompat.getColor(utils.resources, R.color.charging, null)
                } else {
                    utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT)
                }
            )
            
            // Just add standard padding
            utils.viewHeight += utils.padding16 + utils.drawableSize
        } else {
            utils.drawVector(
                canvas,
                batteryIcon,
                utils.horizontalRelativePoint.toInt(),
                (
                    utils.viewHeight + utils.padding16 +
                        utils.getVerticalCenter(
                            utils.getPaint(utils.mediumTextSize),
                        )
                ).toInt(),
                if (batteryIsCharging) {
                    ResourcesCompat.getColor(utils.resources, R.color.charging, null)
                } else {
                    utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT)
                },
            )
            utils.viewHeight += utils.padding16 + utils.getTextHeight() + utils.padding16
        }
    }

    internal fun drawPercentage(
        canvas: Canvas,
        utils: Utils,
        batteryLevel: Int,
        flags: BooleanArray = booleanArrayOf(),
    ) {
        if (flags.getOrNull(AlwaysOnCustomView.FLAG_CIRCULAR_CLOCK) == true) {
            
            // For circular style, position the battery percentage below the date
            utils.drawRelativeText(
                canvas,
                batteryLevel.toString() + PERCENT,
                utils.padding16,
                utils.padding16,
                utils.getPaint(
                    utils.smallTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT)
                ),
                0 // Centered
            )
        } else {
            utils.drawRelativeText(
                canvas,
                batteryLevel.toString() + PERCENT,
                utils.padding16,
                utils.padding16,
                utils.getPaint(
                    utils.mediumTextSize,
                    utils.prefs.get(P.DISPLAY_COLOR_BATTERY, P.DISPLAY_COLOR_BATTERY_DEFAULT),
                ),
            )
        }
    }
}
