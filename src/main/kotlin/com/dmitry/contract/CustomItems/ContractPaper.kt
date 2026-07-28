package com.dmitry.contract.CustomItems

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class ContractPaper(settings: Settings): Item(settings) {

    //rmb use
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        //getting held item
        val stack: ItemStack = user.getStackInHand(hand)

        //not doing anything on the client side
        if (world.isClient) {
            return TypedActionResult.pass(stack)
        }

        //putting data
        val dataStored: String = "Hello world"
        stack.orCreateNbt.putString("data", dataStored)

        return TypedActionResult.success(stack)
    }
}