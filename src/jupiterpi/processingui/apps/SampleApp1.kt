package jupiterpi.processingui.apps

import jupiterpi.processingui.ProcessingUIApp
import jupiterpi.processingui.ui.*
import processing.core.PVector
import java.awt.Color

class SampleApp1 : ProcessingUIApp(1920, 1080) {
    data class ClickCounterState(
        var timesClicked: Int = 0,
    )

    override fun create(context: Context, window: VerticalLayout) {
        // state

        context.states += ClickCounterState()

        // ui
        val label: (name: String) -> (context: Context) -> String = { name: String ->
            { context -> "$name (${context.getState<ClickCounterState>().timesClicked})" }
        }
        val clickHandler: (context: Context, clickPos: PVector) -> Unit
                = { context , _ -> context.getState<ClickCounterState>().timesClicked++ }

        window += Button(context, label("Hello 1"), clickHandler)
        window += Button(context, label("Hello 2 with more text"), clickHandler)

        window += HorizontalLayout(context, padding = PVector(0F, 0F)).also {
            it += Button(context, label("Inside 1"), clickHandler)
            it += Button(context, label("Inside 2"), clickHandler)
            it += Button(context, label("Inside 3"), clickHandler)
        }

        window += HorizontalLayout(context, padding = PVector(0F, 0F)).also {
            repeat(2) { _ ->
                it += object : Button(context, { "Test" }) {
                    override val width get() = 100F + context.getState<ClickCounterState>().timesClicked * 20F
                }
            }
        }

        window += VerticalLayout(context, backgroundColor = Color(190, 90, 120), stretchToWidth = 500F).also {
            repeat(3) { i ->
                it += Button(context, label("Vertical $i"), clickHandler)
            }
            repeat(2) { i ->
                it += Label(context, {"Label $i"})
            }
        }

        window += HorizontalLayout(context, padding = PVector(0F, 0F)).also {
            it += Button(context, {"reset"}, { context, _ -> context.getState<ClickCounterState>().timesClicked = 0 })
            it += Button(context, {"exit"}, { _, _ -> exit() })
        }
    }
}