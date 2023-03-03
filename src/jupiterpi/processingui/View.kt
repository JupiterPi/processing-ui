package jupiterpi.processingui

import processing.core.PApplet
import processing.core.PVector
import java.awt.Color

abstract class View(
    val id: String,
    var parent: Layout? = null,
    val defaultPos: PVector = PVector(0F, 0F),
    val defaultWidth: Float = 0F,
    val defaultHeight: Float = 0F,
) {
    open val pos get() = parent?.getChildPosition(this) ?: defaultPos
    open val width get() = parent?.getChildWidth(this) ?: defaultWidth
    open val height get() = parent?.getChildHeight(this) ?: defaultHeight

    abstract fun render(sketch: PApplet)
    open fun onclick(clickPos: PVector) {}
}

class Button(
    private val label: String,
) : View(
    "button_${label}",
    defaultWidth = 250F,
    defaultHeight = 60F,
) {
    override val height: Float = 60F

    private var clicked = true

    override fun render(sketch: PApplet) {
        sketch.rect(pos, width, height)
        sketch.text(
            label,
            pos + PVector(20F, 10F),
            textSize = 30F,
            fillColor = if (clicked) Color.GREEN else Color.RED,
        )
    }

    override fun onclick(clickPos: PVector) {
        clicked = !clicked
    }
}