package dev.maanraj514.idolplugin.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.inventory.ItemStack

fun String.toComponent(): Component {
    return MiniMessage.miniMessage().deserialize(this)
}
fun List<ItemStack>.print() {
    for (item in this) {
        println("itemtype = ${item.type.name}")
    }
}
object MessageUtil {
}