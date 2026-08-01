package com.dmitry.contract

import net.fabricmc.api.ModInitializer
import com.dmitry.contract.items.ContractPaper
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import org.slf4j.LoggerFactory

object Contract : ModInitializer {
	const val MOD_ID: String = "contract"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		//initializing mod items
		LOGGER.info("Initialisation of Mod Items...")
		ModItems.init()
		LOGGER.info("Mod items were initialised successfully.")

		//initializing creative tab (ItemGroup)
		LOGGER.info("Initialisation of Creative Tab...")
		ModItemTab.init()
		LOGGER.info("Creative tab was initialised successfully.")

		ServerPlayNetworking.registerGlobalReceiver(ModNetworking.SIGN_PACKET) {server, player, handler, buf, responseSender ->
			val isSigned = buf.readBoolean()

			server.execute {
				val stack: ItemStack = ItemStack(ModItems.FINISHED_CONTRACT_PAPER)
				player.setStackInHand(Hand.MAIN_HAND, stack)
			}
		}

		ServerPlayNetworking.registerGlobalReceiver(ModNetworking.NBT_PACKET) {server, player, handler, buf, responseSender ->
			val stack = player.getStackInHand(Hand.MAIN_HAND)
			val itemInstance = stack.item as ContractPaper

			itemInstance.save(stack, buf)

			server.execute {}
		}
	}

	fun id(path: String): Identifier = Identifier(MOD_ID, path)
}
