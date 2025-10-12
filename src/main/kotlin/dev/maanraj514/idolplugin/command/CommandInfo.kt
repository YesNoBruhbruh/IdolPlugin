package dev.maanraj514.idolplugin.command

annotation class CommandInfo(
    val name: String,
    val description: String,
    val permission: String,
    val playerOnly: Boolean = true)
