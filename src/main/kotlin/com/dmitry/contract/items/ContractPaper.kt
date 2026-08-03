package com.dmitry.contract.items

import com.dmitry.contract.ContractClientServerBridge
import com.dmitry.contract.ModItems
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class ContractPaper(settings: Settings): Item(settings) {

    //glint when the contract is signed by both sides
    override fun hasGlint(stack: ItemStack): Boolean {
        return stack.item  == ModItems.SIGNED_CONTRACT_PAPER
    }

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

    //saving to nbt
    fun save(item: ItemStack, data: List<Pair<String, Boolean>>) {
        val nbt = item.orCreateNbt
        var count: Int = 0
        for (entry in data) {
            nbt.putString("line$count", entry.first)
            nbt.putBoolean("check$count", entry.second)
            count++
        }
    }

    //signing contrac paper
    fun onSign(player: PlayerEntity) {
        val nbtToCopy = player.getStackInHand(Hand.MAIN_HAND).orCreateNbt.apply { putString("author", player.entityName) }
        val newStack = ItemStack(ModItems.FINISHED_CONTRACT_PAPER).apply { setNbt(nbtToCopy.copy()) }
        player.setStackInHand(
            Hand.MAIN_HAND,
            newStack
        )

    }

    //signing final contract paper
    fun onFinalSign(player: PlayerEntity) {
        val nbtToCopy = player.getStackInHand(Hand.MAIN_HAND).orCreateNbt.apply { putString("signee", player.entityName) }
        val newStack = ItemStack(ModItems.SIGNED_CONTRACT_PAPER).apply { setNbt(nbtToCopy.copy()) }
        player.setStackInHand(
            Hand.MAIN_HAND,
            newStack
        )
    }

    //show tooltips
    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext?) {
        val author = stack.orCreateNbt.getString("author")
        if (author.isNotBlank()) {
            tooltip.add(Text.translatable("author_is").append(Text.literal(author)).formatted(Formatting.DARK_GRAY))
        }
        val signee = stack.orCreateNbt.getString("signee")
        if (signee.isNotBlank()) {
            tooltip.add(Text.translatable("signee_is").append(Text.literal(signee)).formatted(Formatting.DARK_GRAY))
        }
        super.appendTooltip(stack, world, tooltip, context)
    }
}