package com.dmitry.contract

import net.minecraft.item.ItemStack

interface ContractScreenOpener {
    fun open(contractItem: ItemStack)
}

object ContractClientServerBridge {
    var opener: ContractScreenOpener? = null
}