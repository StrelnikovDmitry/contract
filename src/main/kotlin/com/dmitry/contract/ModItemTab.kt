package com.dmitry.contract

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text

object ModItemTab {

    //creating a registry key for Creative Tab
    val REGISTRY_KEY: RegistryKey<ItemGroup> = RegistryKey.of<ItemGroup>(RegistryKeys.ITEM_GROUP, Contract.id("contract_mod_tab"))

    //registering creative tab
    val CONTRACT_CREATIVE_TAB: ItemGroup = Registry.register(
        Registries.ITEM_GROUP,
        REGISTRY_KEY,
        FabricItemGroup.builder()
            .icon { ItemStack(ModItems.SIGNED_CONTRACT_PAPER) }
            .displayName(Text.translatable("contract_mod_tab_display_name"))
            .build()
    )

    //adding items to the tab (+initializing it)
    fun init() {
        ItemGroupEvents.modifyEntriesEvent(REGISTRY_KEY)
            .register { entries ->
                entries.add(ModItems.CONTRACT_PAPER)
                entries.add(ModItems.FINISHED_CONTRACT_PAPER)
                entries.add(ModItems.SIGNED_CONTRACT_PAPER)
            }
    }
}