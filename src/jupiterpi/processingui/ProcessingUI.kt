package jupiterpi.processingui

import processing.core.PApplet
import processing.core.PVector
import processing.event.MouseEvent
import java.awt.Color

fun main() = PApplet.main(ProcessingUI::class.qualifiedName)

class ProcessingUI : PApplet() {
    override fun settings() {
        size(windowView.width.toInt(), windowView.height.toInt())
    }

    val context = Context(
        calculateTextWidth = { text -> textWidth(text) },
        debugLayouts = false,
    )

    override fun draw() {
        background(Color.WHITE.rgb)
        windowView.render(this)
    }

    override fun mouseClicked(event: MouseEvent) {
        windowView.onclick(PVector(event.x.toFloat(), event.y.toFloat()))
    }

    private val windowView = object : VerticalLayout(context, "window") {
        override val pos: PVector
            get() = PVector(0F, 0F)
        override val width: Float
            get() = 1920F
        override val height: Float
            get() = 1080F
    }

    init {
        // state

        context.states += ClickCounterState()

        // ui
        val label: (name: String) -> (context: Context) -> String = { name: String ->
            { context -> "$name (${context.getState<ClickCounterState>().timesClicked})" }
        }
        val clickHandler: (context: Context, clickPos: PVector) -> Unit
                = { context , _ -> context.getState<ClickCounterState>().timesClicked++ }

        windowView += Button(context, label("Hello 1"), clickHandler)
        windowView += Button(context, label("Hello 2 with more text"), clickHandler)

        val layout1 = HorizontalLayout(context, padding = PVector(0F, 0F))
        layout1 += Button(context, label("Inside 1"), clickHandler)
        layout1 += Button(context, label("Inside 2"), clickHandler)
        layout1 += Button(context, label("Inside 3"), clickHandler)
        windowView += layout1

        val layout2 = HorizontalLayout(context, padding = PVector(0F, 0F))
        repeat(2) {
            layout2 += object : Button(context, { "Test" }) {
                override val width get() = 100F + context.getState<ClickCounterState>().timesClicked * 20F
            }
        }
        windowView += layout2

        val layout3 = VerticalLayout(context, backgroundColor = Color(190, 90, 120), stretchToWidth = 500F)
        repeat(3) { i ->
            layout3 += Button(context, label("Vertical $i"), clickHandler)
        }
        repeat(2) { i ->
            layout3 += Label(context, {"Label $i"})
        }
        windowView += layout3

        windowView += Button(context, {"reset"}, { context, _ -> context.getState<ClickCounterState>().timesClicked = 0 })
    }

    data class ClickCounterState(
        var timesClicked: Int = 0,
    )
}