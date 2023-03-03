package jupiterpi.processingui

import jupiterpi.processingui.apps.SampleApp1
import jupiterpi.processingui.ui.*
import processing.core.PApplet
import processing.core.PVector
import processing.event.MouseEvent
import java.awt.Color
import kotlin.system.exitProcess

fun main() = PApplet.main(ProcessingUI::class.qualifiedName)

class ProcessingUI(
    val app: ProcessingUIApp = SampleApp1()
) : PApplet() {
    override fun settings() {
        size(app.windowWidth, app.windowHeight)
        app.create(context, windowView)
    }

    val context = Context(
        calculateTextWidth = { text -> textWidth(text) },
        debugLayouts = false,
    )

    override fun draw() {
        background(app.backgroundColor.rgb)
        windowView.render(this)
    }

    override fun mouseClicked(event: MouseEvent) {
        windowView.onClick(PVector(event.x.toFloat(), event.y.toFloat()))
    }

    override fun mouseMoved(event: MouseEvent) {
        windowView.onMouseMove(PVector(event.x.toFloat(), event.y.toFloat()))
    }

    private val windowView = object : VerticalLayout(context, "window") {
        override val pos = PVector(0F, 0F)
        override val width = app.windowWidth.toFloat()
        override val height = app.windowHeight.toFloat()
    }
}

abstract class ProcessingUIApp(
    val windowWidth: Int,
    val windowHeight: Int,
    val backgroundColor: Color = Color.WHITE,
) {
    abstract fun create(context: Context, window: VerticalLayout)

    fun exit(): Nothing = exitProcess(0)
}