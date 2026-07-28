package com.dmitry.contract

interface ContractScreenOpener {
    fun open()
}

object ContractClientServerBridge {
    var opener: ContractScreenOpener? = null
}