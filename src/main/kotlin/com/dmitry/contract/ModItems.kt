package com.dmitry.contract

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

object ModItems {
    val CONTRACT_PAPER: Item = Registry.register(
        Registries.ITEM,
        Contract.id("contract_paper"),
        Item(FabricItemSettings())
    )

    fun init() {}
}