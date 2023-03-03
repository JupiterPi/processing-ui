package jupiterpi.processingui

class Context {
    val state = mutableMapOf<String, State>()
}

interface State

class MyState : State {
    val myValue
        get() = "hi"
}