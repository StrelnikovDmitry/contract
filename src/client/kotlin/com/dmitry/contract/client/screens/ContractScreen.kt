package com.dmitry.contract.client.screens

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.EditBoxWidget
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ContractScreen : Screen(Text.translatable("contract_screen")) {
    val textureWidth: Int = 146
    val textureHeight: Int = 180
    val texture = Identifier("contract", "textures/gui/contract_screen.png")

    lateinit var contractTextField: EditBoxWidget

    //screen should close after escape button is pressed
    override fun shouldCloseOnEsc(): Boolean = true

    //initializing widgets
    override fun init() {
        super.init()

        val x: Int = (width - textureWidth) / 2 + 16
        val y: Int = 16

        contractTextField = EditBoxWidget(
            textRenderer,
            x, y,
            textureWidth - 32, textureHeight - (2 * y),
            Text.translatable("contract_text"),
            Text.translatable("contract_text")
        )

        addDrawableChild(contractTextField)
    }

    //rendering screen
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        //rendering the screen on which all of that will be displayed
        super.render(context, mouseX, mouseY, delta)

        //darkens the background
        this.renderBackground(context)

        //texture is drawn from left to right and from up to down
        //getting  coordinates where to draw texture
        val x: Int = (width - textureWidth) / 2

        //the game multiplies coordinates and texture sizes by gui scale, 3 is the default option in vanilla book.
        val y: Int = 3

        //drawing
        context.drawTexture(texture, x, y, 0f, 0f, textureWidth, textureHeight, textureWidth, textureHeight)
    }
}