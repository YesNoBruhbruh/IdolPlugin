package dev.maanraj514.idolplugin.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer
import java.util.function.Function

class GUIButton(
    val iconCreator: Function<Player, ItemStack>,
    val eventConsumer: Consumer<InventoryClickEvent>
) {
}