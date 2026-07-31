package com.dmitry.contract.client.screens

import com.dmitry.contract.client.widgets.ContractCheckbox
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.sound.PositionedSoundInstance
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

class ContractScreen : Screen(Text.translatable("contract_screen")) {
    @Suppress("PROPERTY_HIDES_JAVA_FIELD")
    val client: MinecraftClient = MinecraftClient.getInstance()

    val textureWidth: Int = 146
    val textureHeight: Int = 180
    val texture = Identifier("contract", "textures/gui/contract_screen.png")

    val buttonTextureSide: Int = 5
    val buttonTextureCross = Identifier("contract", "textures/gui/cross.png")
    val buttonTextureCheck = Identifier("contract", "textures/gui/check.png")

    //index of the selected line
    var index: Int = 0

    val lineGap = 9

    val maxLineAmount: Int = (textureWidth-19)/lineGap

    val lines: MutableList<Pair<TextFieldWidget, ContractCheckbox>> = mutableListOf()

    fun update(line: Pair<TextFieldWidget, ContractCheckbox>?) {
        if (line != null) {

            //adding the line to the list
            lines.add(line)

            //adding widgets
            addSelectableChild(line.first)
            addSelectableChild(line.second)
        }

        else {
            client.soundManager.play(PositionedSoundInstance.master
                (SoundEvents.BLOCK_NOTE_BLOCK_HARP.value(), 0.5f, 1.0f)
            )
        }
    }

    //key press listener
    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {

        //creating new string by pressing enter
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            update(buildLine())
            index = lines.size - 1
            setFocused(lines[index].first)
            return true
        }

        //screen should close after escape button is pressed
        else if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            close()
            return true
        }

        //removing line or the last letter by pressing backspace
        else if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
            if (!lines.isEmpty()) {
                val pair = lines[index]

                //if there is none focused
                if (!isFocused) {
                    return true
                }

                //if there is no text on the line...
                else if (pair.first.text.isEmpty()) {

                    //and the checkbox is noot checked...
                    if (!pair.second.isChecked) {
                        //remove the line
                        remove(lines[index].first)
                        remove(lines[index].second)
                        lines.removeAt(index)

                        //redrawing other lines
                        redrawLines(index, lines.size)

                        if (index > 0) {
                            //go up if there is something
                            index--
                            setFocused(lines[index].first)
                            return true
                        }
                        //skip if there is none
                        else {
                            return true
                        }
                    }
                    //skip if the checkbox is checked
                    else {
                        return true
                    }
                } else {
                    //if string is editable
                    if (!pair.second.isChecked) {
                        //delete the last symbol
                        return super.keyPressed(keyCode, scanCode, modifiers)
                    }
                    //if it is not - skip
                    else {
                        return true
                    }
                }
            } else {return true}
        }

        //going up
        else if (keyCode == GLFW.GLFW_KEY_UP) {
            if (index>0) {
                index--
                setFocused(lines[index].first)
                return true
            } else {return true}
        }

        //going down
        else if (keyCode == GLFW.GLFW_KEY_DOWN) {
            if (index+1<lines.size) {
                index++
                setFocused(lines[index].first)
                return true
            } else {return true}
        }

        //giving a key further
        else {
            return super.keyPressed(keyCode, scanCode, modifiers)
        }
    }

    override fun mouseClicked(x: Double, y: Double, button: Int): Boolean {
        val res = super.mouseClicked(x, y, button)
        if (focused is TextFieldWidget) {
            val currentFocus = lines.find { it.first == focused }
            index = lines.indexOf(currentFocus)
        }
        return res
    }

    //build a line and a button near it
    fun buildLine(): Pair<TextFieldWidget, ContractCheckbox>? {

        val linesAmount = lines.size

        if (linesAmount<maxLineAmount) {

            //building a line
            val x: Int = (width - textureWidth) / 2 + 16 + 12
            val y: Int = 19 + (linesAmount * lineGap)

            val contractTextField = TextFieldWidget(
                textRenderer,
                x, y,
                textureWidth - 32, 5,
                Text.translatable("contract_text_point$linesAmount")
            ).apply {
                setEditableColor(-12303292)
                setUneditableColor(-13587920)
                setMaxLength(((textureWidth - 32) / 6) - 2)
                setDrawsBackground(false)
            }

            //building a button
            val x_button: Int = (width - textureWidth) / 2 + 16
            val y_button: Int = y + 2

            val checkBox = ContractCheckbox(
                x_button,
                y_button,
                buttonTextureSide,
                buttonTextureCheck,
                buttonTextureCross,
                contractTextField,
                Text.translatable("contract_mark_point$linesAmount")
            )

            return Pair(contractTextField, checkBox)
        }
        return null
    }

    //redrawing lines from x to y-1
    fun redrawLines(x: Int = 0, y: Int = lines.size) {
        if (lines.isNotEmpty() && x<y) {
            val tempLines: List<Pair<String, Boolean>> = lines.slice(x..y - 1).map { Pair(it.first.text, it.second.isChecked) }

            //deleting widgets from the screen
            lines.subList(x, y).forEach {
                remove(it.first)
                remove(it.second)
            }

            //deleting widgets from the lines list
            lines.subList(x, y).clear()

            //building new lines
            for (i in (x..y - 1)) {
                val line = buildLine()
                line?.first?.setText(tempLines[i-x].first)
                update(line)

                //checking if the line was checked previously
                if (tempLines[i-x].second) {
                    line?.second?.onPress()
                }
            }
            //setting right focus
            setFocused(lines[index].first)
        }
    }

    //initializing widgets
    override fun init() {
        super.init()
        redrawLines()
    }

    //rendering screen
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        //darkens the background
        this.renderBackground(context)

        //texture is drawn from left to right and from up to down
        //getting  coordinates where to draw texture
        val x: Int = (width - textureWidth) / 2

        //the game multiplies coordinates and texture sizes by gui scale, 3 is the default option in vanilla book.
        val y: Int = 3

        for (j in lines)
            j.first.render(context, mouseX, mouseY, delta)

        //drawing
        context.drawTexture(texture, x, y, 0f, 0f, textureWidth, textureHeight, textureWidth, textureHeight)
        context.draw()

        for (j in lines)
            j.second.render(context, mouseX, mouseY, delta)
    }
}