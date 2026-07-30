package com.dmitry.contract.client.screens

import com.dmitry.contract.client.widgets.ContractCheckbox
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.CheckboxWidget
import net.minecraft.client.gui.widget.CyclingButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.command.argument.IdentifierArgumentType.identifier
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ContractScreen : Screen(Text.translatable("contract_screen")) {
    val textureWidth: Int = 146
    val textureHeight: Int = 180
    val texture = Identifier("contract", "textures/gui/contract_screen.png")

    val buttonTextureSide: Int = 5
    val buttonTextureCross = Identifier("contract", "textures/gui/cross.png")
    val buttonTextureCheck = Identifier("contract", "textures/gui/check.png")

    val lineGap = 7

    val maxLineAmount: Int = textureWidth/lineGap

    val lines: MutableList<Pair<TextFieldWidget, ButtonWidget>> = mutableListOf()

    //screen should close after escape button is pressed
    override fun shouldCloseOnEsc(): Boolean = true

    //build a line and a button near it
    fun buildLine(i: Int): Pair<TextFieldWidget, ContractCheckbox>? {
        if (i<maxLineAmount) {

            //building a line
            val x: Int = (width - textureWidth) / 2 + 16 + 12
            val y: Int = 19 + (i * lineGap)

            var contractTextField = TextFieldWidget(
                textRenderer,
                x, y,
                textureWidth - 32, 5,
                Text.translatable("contract_text_point$i")
            ).apply {
                setEditableColor(-12303292)
                setUneditableColor(-13587920)
                setMaxLength(((textureWidth - 32) / 6) - 2)
                setDrawsBackground(false)
            }

            //building a button
            val x_button: Int = (width - textureWidth) / 2 + 16
            val y_button: Int = y + 2

            var checkBox = ContractCheckbox(
                x_button,
                y_button,
                buttonTextureSide,
                buttonTextureCheck,
                buttonTextureCross,
                contractTextField,
                Text.translatable("contract_mark_point$i")
            )

            return Pair(contractTextField, checkBox)
        }
        return null
    }

    //initializing widgets
    override fun init() {
        super.init()

        val line = buildLine(0)
        if (line!=null) {
            addDrawableChild(line.first)
            addDrawableChild(line.second)
        }
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

        //drawing
        context.drawTexture(texture, x, y, 0f, 0f, textureWidth, textureHeight, textureWidth, textureHeight)

        //rendering the screen on which all of that will be displayed
        super.render(context, mouseX, mouseY, delta)
    }
}