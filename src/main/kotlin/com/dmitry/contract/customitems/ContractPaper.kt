package com.dmitry.contract.customitems

import com.dmitry.contract.ContractClientServerBridge
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class ContractPaper(settings: Settings): Item(settings) {

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val data = stack.nbt?.getString("data")
        if (data!=null) {
            tooltip.add(Text.literal(data))
        }
    }

    //rmb use
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        //getting held item
        val stack: ItemStack = user.getStackInHand(hand)

        //opening screen on client
        if (world.isClient) {
            ContractClientServerBridge.opener?.open()
            return TypedActionResult.success(stack)
        }

        //skipping on the server side
        return TypedActionResult.pass(stack)
    }
}