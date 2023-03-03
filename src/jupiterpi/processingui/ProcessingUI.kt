package jupiterpi.processingui

import processing.core.PApplet
import processing.core.PVector
import processing.event.MouseEvent

fun main() = PApplet.main(ProcessingUI::class.qualifiedName)

class ProcessingUI : PApplet() {
    override fun settings() {
        size(windowView.width.toInt(), windowView.height.toInt())
    }

    override fun draw() {
        windowView.render(this)
    }

    override fun mouseClicked(event: MouseEvent) {
        windowView.onclick(PVector(event.x.toFloat(), event.y.toFloat()))
    }

    private val windowView = object : VerticalLayout("window") {
        override val pos: PVector
            get() = PVector(0F, 0F)
        override val width: Float
            get() = 1920F
        override val height: Float
            get() = 1080F
    }

    init {
        windowView += Button("Hello 1")
        windowView += Button("Hello 2")

        val layout = HorizontalLayout(padding = PVector(0F, 0F))
        layout += Button("Inside 1")
        layout += Button("Inside 2")
        layout += Button("Inside 3")
        windowView += layout
    }
}