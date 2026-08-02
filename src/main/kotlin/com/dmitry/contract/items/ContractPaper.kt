package com.dmitry.contract.items

import com.dmitry.contract.ContractClientServerBridge
import com.dmitry.contract.ModItems
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.network.PacketByteBuf
import net.minecraft.text.Text
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

    fun save(item: ItemStack, data: List<Pair<String, Boolean>>) {
        val nbt = item.orCreateNbt
        var count: Int = 0
        for (entry in data) {
            nbt.putString("line$count", entry.first)
            nbt.putBoolean("check$count", entry.second)
            count++
        }
    }

    fun onSign(player: PlayerEntity) {
        val nbtToCopy = player.getStackInHand(Hand.MAIN_HAND).orCreateNbt.apply { putString("author", player.entityName) }
        val newStack = ItemStack(ModItems.FINISHED_CONTRACT_PAPER).apply { setNbt(nbtToCopy.copy()) }
        player.setStackInHand(
            Hand.MAIN_HAND,
            newStack
        )

    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext?) {
        val author = stack.orCreateNbt.getString("author")
        if (author.isNotBlank()) {
            tooltip.add(Text.literal(author))
        }
        super.appendTooltip(stack, world, tooltip, context)
    }
}