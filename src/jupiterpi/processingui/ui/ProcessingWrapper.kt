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
    borderRadius: Float = 0F,
) {
    fill(fillColor)
    stroke(strokeColor)
    strokeWeight(strokeWeight)

    if (borderRadius > 0F) {
        rect(pos.x, pos.y + borderRadius, width, height - 2 * borderRadius)
        rect(pos.x + borderRadius, pos.y, width - 2 * borderRadius, height)
        ellipse(pos.x + borderRadius, pos.y + borderRadius, 2 * borderRadius, 2 * borderRadius)
        ellipse(pos.x + width - borderRadius, pos.y + borderRadius, 2 * borderRadius, 2 * borderRadius)
        ellipse(pos.x + width - borderRadius, pos.y + height - borderRadius, 2 * borderRadius, 2 * borderRadius)
        ellipse(pos.x + borderRadius, pos.y + height - borderRadius, 2 * borderRadius, 2 * borderRadius)
    } else {
        rect(pos.x, pos.y, width, height)
    }
}

fun PApplet.ellipse(
    pos: PVector,
    width: Float,
    height: Float,
    fillColor: Color? = Color.BLACK,
    strokeColor: Color? = null,
    strokeWeight: Float = 10F
) {
    fill(fillColor)
    stroke(strokeColor)
    strokeWeight(strokeWeight)
    ellipse(pos.x, pos.y, width, height)
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