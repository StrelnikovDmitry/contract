package com.dmitry.contract

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Rarity
import com.dmitry.contract.CustomItems.*

object ModItems {
    //items
    val CONTRACT_PAPER: Item = Registry.register(
        Registries.ITEM,
        Contract.id("contract_paper"),
        ContractPaper(FabricItemSettings().maxCount(1))
    )

    val FINISHED_CONTRACT_PAPER: Item = Registry.register(
        Registries.ITEM,
        Contract.id("finished_contract_paper"),
        Item(FabricItemSettings().maxCount(1))
    )

    val SIGNED_CONTRACT_PAPER: Item = Registry.register(
        Registries.ITEM,
        Contract.id("signed_contract_paper"),
        Item(FabricItemSettings().maxCount(1).rarity(Rarity.EPIC))
    )

    //---

    //initializing items
    fun init() {}
}