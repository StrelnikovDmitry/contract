package com.dmitry.contract

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

interface ContractScreenOpener {
    fun open(x: ItemStack, y: PlayerEntity)
}

object ContractClientServerBridge {
    var opener: ContractScreenOpener? = null
}