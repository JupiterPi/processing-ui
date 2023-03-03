package jupiterpi.processingui

import processing.core.PApplet
import processing.core.PVector
import java.awt.Color
import java.util.*

abstract class Layout(
    id: String = "layout_${UUID.randomUUID()}",
) : View(id) {
    val views = mutableListOf<View>()

    operator fun plusAssign(view: View) = appendView(view)
    fun appendView(view: View) {
        view.parent = this
        views += view
    }

    override fun render(sketch: PApplet) {
        sketch.rect(pos, width, height, fillColor = null, strokeColor = Color.RED)
        views.forEach { it.render(sketch) }
    }

    override fun onclick(clickPos: PVector) {
        views.forEach {
            if (
                (clickPos.x in it.pos.x..it.pos.x + it.width)
                && (clickPos.y in it.pos.y..it.pos.y + it.height)
            ) {
                it.onclick(clickPos)
            }
        }
    }

    abstract fun getChildPosition(view: View): PVector?
    abstract fun getChildWidth(view: View): Float?
    abstract fun getChildHeight(view: View): Float?
}

open class VerticalLayout(
    id: String = "vertical_layout_${UUID.randomUUID()}",
    val padding: PVector = PVector(30F, 30F),
    val gap: Float = 30F,
) : Layout(id) {
    override val width get() = if (views.isEmpty()) 0F else 2 * padding.x + views.maxOf { it.width }
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

    override fun getChildWidth(view: View): Float? = null
    override fun getChildHeight(view: View): Float? = null
}

open class HorizontalLayout(
    id: String = "horizontal_layout_${UUID.randomUUID()}",
    val padding: PVector = PVector(30F, 30F),
    val gap: Float = 30F,
) : Layout(id) {
    override val width get() = if (views.isEmpty()) 0F else 2 * padding.x + views.map { it.width }.sum() + (views.size-1) * gap
    override val height get() = if (views.isEmpty()) 0F else 2 * padding.y + views.maxOf { it.height }

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
    override fun getChildHeight(view: View): Float? = null
}