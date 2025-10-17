package dev.maanraj514.idolplugin.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

fun String.toComponent(): Component {
    return MiniMessage.miniMessage().deserialize(this)
}
object MessageUtil {
}