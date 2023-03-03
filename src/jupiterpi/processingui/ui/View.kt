package jupiterpi.processingui.ui

import processing.core.PApplet
import processing.core.PVector
import java.awt.Color
import java.util.*

abstract class View(
    val id: String,
    val context: Context,
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

open class Label(
    context: Context,
    private val label: (context: Context) -> String = { "text" },
    id: String = "label_${UUID.randomUUID()}",
) : View(id, context, defaultWidth = 100F, defaultHeight = 50F) {
    override val height = 30F
    override val width get() = context.calculateTextWidth(label(context))

    override fun render(sketch: PApplet) {
        sketch.text(label(context), pos, textSize = 30F)
    }
}

open class Button(
    context: Context,
    private val label: (context: Context) -> String = { "button" },
    private val onclick: (context: Context, clickPos: PVector) -> Unit = { _, _ -> },
    id: String = "button_${UUID.randomUUID()}",
) : View(
    id,
    context,
    defaultWidth = 250F,
    defaultHeight = 60F,
) {
    override val height: Float = 60F
    override val width get() = parent?.getChildWidth(this) ?: (40F + context.calculateTextWidth(label(context)))

    override fun render(sketch: PApplet) {
        sketch.rect(pos, width, height, borderRadius = 10F)
        sketch.text(
            label(context),
            pos + PVector(20F, 10F),
            textSize = 30F,
            fillColor = Color.WHITE
        )
    }

    override fun onclick(clickPos: PVector) = onclick(context, clickPos)
}