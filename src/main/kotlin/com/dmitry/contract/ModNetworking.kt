package com.dmitry.contract

import net.minecraft.util.Identifier

object ModNetworking {
    val SIGN_PACKET: Identifier = Identifier("contract", "sign_contract")

    val NBT_PACKET: Identifier = Identifier("contract", "contract_nbt")
}