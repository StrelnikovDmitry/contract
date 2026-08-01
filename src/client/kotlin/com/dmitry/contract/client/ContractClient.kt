package com.dmitry.contract.client

import net.fabricmc.api.ClientModInitializer
import com.dmitry.contract.ContractScreenOpener
import com.dmitry.contract.ContractClientServerBridge
import com.dmitry.contract.client.screens.ContractScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack

object ContractClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		//creating a client-server bridge
		ContractClientServerBridge.opener = object : ContractScreenOpener {

			//opening contract screen
			override fun open(contractItem: ItemStack) {
				MinecraftClient.getInstance().setScreen(ContractScreen(contractItem))
			}
		}
	}
}