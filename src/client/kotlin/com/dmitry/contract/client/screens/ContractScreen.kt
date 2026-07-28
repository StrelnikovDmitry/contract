package com.dmitry.contract.client.screens

import com.dmitry.contract.Contract
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

class ContractScreen: Screen(Text.translatable("contract_screen")) {

    //screen should close after escape button is pressed
    override fun shouldCloseOnEsc(): Boolean = true
}