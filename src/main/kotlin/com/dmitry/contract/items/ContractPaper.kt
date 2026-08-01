package com.dmitry.contract.items

import com.dmitry.contract.ContractClientServerBridge
import com.dmitry.contract.ModItems
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class ContractPaper(settings: Settings): Item(settings) {

    //rmb use
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        //getting held item
        val stack: ItemStack = user.getStackInHand(hand)

        //opening screen on client
        if (world.isClient) {
            ContractClientServerBridge.opener?.open(stack)
            return TypedActionResult.success(stack)
        }

        //skipping on the server side
        return TypedActionResult.pass(stack)
    }

    fun save(item: ItemStack, packet: PacketByteBuf) {
        val size = packet.readInt()
        var count: Int = 0
        for (i in 0..size-1) {
            item.orCreateNbt.putString("line$count", packet.readString())
            item.orCreateNbt.putBoolean("check$count", packet.readBoolean())
            count++
        }
    }

    fun onSign(player: PlayerEntity) {
        val nbtToCopy = player.getStackInHand(Hand.MAIN_HAND).nbt
        val newStack = ItemStack(ModItems.FINISHED_CONTRACT_PAPER).apply { setNbt(nbtToCopy?.copy()) }
        player.setStackInHand(
            Hand.MAIN_HAND,
            newStack
        )

    }
}