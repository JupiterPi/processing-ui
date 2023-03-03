package jupiterpi.processingui.ui

import processing.core.PApplet
import processing.core.PVector
import java.awt.Color
import java.util.*

abstract class Layout(
    context: Context,
    id: String = "layout_${UUID.randomUUID()}",
    val backgroundColor: Color? = null,
) : View(id, context) {
    val views = mutableListOf<View>()

    operator fun plusAssign(view: View) = appendView(view)
    fun appendView(view: View) {
        view.parent = this
        views += view
    }

    override fun render(sketch: PApplet) {
        if (context.debugLayouts) sketch.rect(pos, width, height, fillColor = null, strokeColor = Color.RED)
        if (backgroundColor != null) sketch.rect(pos, width, height, fillColor = backgroundColor, borderRadius = 10F)
        views.forEach { it.render(sketch) }
    }

    override fun onClick(clickPos: PVector) {
        views.forEach {
            if (isInside(clickPos, it.pos, it.width, it.height)) it.onClick(clickPos)
        }
    }

    override fun onMouseMove(mousePos: PVector?) {
        views.forEach {
            if (mousePos != null && isInside(mousePos, it.pos, it.width, it.height)) {
                it.onMouseMove(mousePos)
            } else {
                it.onMouseMove(null)
            }
        }
    }

    private fun isInside(pos: PVector, rectPos: PVector, width: Float, height: Float)
    = (pos.x in rectPos.x..rectPos.x + width) && (pos.y in rectPos.y..rectPos.y + height)

    abstract fun getChildPosition(view: View): PVector?
    abstract fun getChildWidth(view: View): Float?
    abstract fun getChildHeight(view: View): Float?
}

open class VerticalLayout(
    context: Context,
    id: String = "vertical_layout_${UUID.randomUUID()}",
    val padding: PVector = PVector(30F, 30F),
    val gap: Float = 30F,
    val stretchToWidth: Float? = null,
    backgroundColor: Color? = null,
) : Layout(context, id, backgroundColor) {
    override val width: Float
    get() {
        if (stretchToWidth != null) return stretchToWidth
        return if (views.isEmpty()) 0F else 2 * padding.x + views.maxOf { it.width }
    }
    override val height get() = if (views.isEmpty()) 0F else 2 * padding.y + views.map { it.height }.sum() + (views.size-1) * gap

    override fun getChildPosition(view: View): PVector? {
        val index = views.indexOf(view)
        return if (index >= 1) {
            val viewBefore = views[index-1]
            viewBefore.pos + PVector(0F, viewBefore.height) + PVector(0F, gap)
        } else {
            pos + padding
        }
    }

    override fun getChildWidth(view: View): Float? = if (stretchToWidth != null) stretchToWidth - 2 * padding.y else null
    override fun getChildHeight(view: View): Float? = null
}

open class HorizontalLayout(
    context: Context,
    id: String = "horizontal_layout_${UUID.randomUUID()}",
    val padding: PVector = PVector(30F, 30F),
    val gap: Float = 30F,
    val stretchToHeight: Float? = null,
    backgroundColor: Color? = null,
) : Layout(context, id, backgroundColor) {
    override val width get() = if (views.isEmpty()) 0F else 2 * padding.x + views.map { it.width }.sum() + (views.size-1) * gap
    override val height: Float
    get() {
        if (stretchToHeight != null) return stretchToHeight
        return if (views.isEmpty()) 0F else 2 * padding.y + views.maxOf { it.height }
    }

    override fun getChildPosition(view: View): PVector? {
        val index = views.indexOf(view)
        return if (index >= 1) {
            val viewBefore = views[index-1]
            viewBefore.pos + PVector(viewBefore.width, 0F) + PVector(gap, 0F)
        } else {
            pos + padding
        }
    }

    override fun getChildWidth(view: View): Float? = null
    override fun getChildHeight(view: View): Float? = if (stretchToHeight != null) stretchToHeight - 2 * padding.x else null
}