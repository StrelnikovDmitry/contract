package com.dmitry.contract.client.widgets

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.widget.CheckboxWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.Text
import net.minecraft.util.Identifier

class ContractCheckbox(
    x: Int,
    y: Int,
    val textureSide: Int,
    val textureTrue: Identifier,
    val textureFalse: Identifier,
    val linkedLine: TextFieldWidget,
    message: Text
):
    CheckboxWidget(x, y, textureSide, textureSide, message, false) {

    override fun renderButton(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        if (isChecked) {
            context.drawTexture(
                textureTrue,
                x,
                y,
                0f,
                0f,
                textureSide,
                textureSide,
                textureSide,
                textureSide
            )
        } else {
            context.drawTexture(
                textureFalse,
                x,
                y,
                0f,
                0f,
                textureSide,
                textureSide,
                textureSide,
                textureSide
            )
        }
    }

    override fun onPress() {
        super.onPress()
        if (isChecked) {
            linkedLine.setEditable(false)
        } else {
            linkedLine.setEditable(true)
        }
    }
}