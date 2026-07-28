package com.dmitry.contract.client.screens

import com.dmitry.contract.Contract
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ContractScreen: Screen(Text.translatable("contract_screen")) {
    val textureWidth: Int = 146
    val textureHeight: Int = 180
    val texture = Identifier("contract", "textures/gui/contract_screen.png")

    //screen should close after escape button is pressed
    override fun shouldCloseOnEsc(): Boolean = true

    //rendering screen
    override fun render(context: DrawContext?, mouseX: Int, mouseY: Int, delta: Float) {
        //rendering the screen on which all of that will be displayed
        super.render(context, mouseX, mouseY, delta)

        //darkens the background
        this.renderBackground(context)

        //texture is drawn from left to right and from up to down
        //getting  coordinates where to draw texture
        val x: Int = (width - textureWidth) / 2

        //for some reason, in the game the gap  is 12 pixels
        val y: Int = 4

        //drawing
        context?.drawTexture(texture, x, y, 0f, 0f, textureWidth, textureHeight, textureWidth, textureHeight)
    }
}