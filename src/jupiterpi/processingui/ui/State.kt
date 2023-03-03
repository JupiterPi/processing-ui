package jupiterpi.processingui.ui

class Context(
    val calculateTextWidth: (text: String) -> Float,
    val debugLayouts: Boolean = false,
) {
    val states = mutableListOf<Any>()

    inline fun <reified T> getState(): T {
        for (state in states) {
            if (state is T) return state
        }
        throw Exception("state does not exist")
    }
}