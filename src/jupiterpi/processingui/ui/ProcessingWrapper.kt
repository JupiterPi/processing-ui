package jupiterpi.processingui.ui

import processing.core.PApplet
import processing.core.PVector
import java.awt.Color

// PVector

operator fun PVector.plus(add: PVector): PVector = copy().add(add)

operator fun PVector.minus(subtract: PVector): PVector = copy().sub(subtract)

// PApplet

fun PApplet.fill(color: Color?) {
    if (color != null) fill(color.rgb) else noFill()
}

fun PApplet.stroke(color: Color?) {
    if (color != null) stroke(color.rgb) else noStroke()
}

fun PApplet.rect(
    pos: PVector,
    width: Float,
    height: Float,
    fillColor: Color? = Color.BLACK,
    strokeColor: Color? = null,
    strokeWeight: Float = 10F,
) {
    fill(fillColor)
    stroke(strokeColor)
    strokeWeight(strokeWeight)
    rect(pos.x, pos.y, width, height)
}

const val TEXT_ALIGN_Y_BOTTOM = 101
fun PApplet.text(
    text: String,
    pos: PVector,
    textSize: Float = 20F,
    fillColor: Color? = Color.BLACK,
) {
    textSize(textSize)
    fill(fillColor)
    textAlign(0, TEXT_ALIGN_Y_BOTTOM)
    text(text, pos.x, pos.y)
}