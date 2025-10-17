package dev.maanraj514.idolplugin.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent

abstract class GUI(private val guiSettings: GUISettings) : GUIHandler {

    val inventory = Bukkit.createInventory(null, guiSettings.guiSize, guiSettings.guiTitle)
    private val buttonMap = mutableMapOf<Int, GUIButton>()
    protected var cancelClicks = false

    fun addButton(slot: Int, button: GUIButton) {
        buttonMap[slot] = button
    }

    fun decorate(player: Player) {
        buttonMap.forEach { (slot, button) -> inventory.setItem(slot, button.iconCreator.apply(player)) }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = cancelClicks

        val slot = event.slot

        val button = buttonMap[slot] ?: return
        button.eventConsumer.accept(event)
    }

    override fun onOpen(event: InventoryOpenEvent) {
        decorate(event.player as Player)
    }
}