package dev.maanraj514.idolplugin.gui

import dev.maanraj514.idolplugin.idol.IdolPlayer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

abstract class GUI(
    protected val idolPlayer: IdolPlayer,
    protected val guiSettings: GUISettings) : GUIHandler {

    val inventory = Bukkit.createInventory(null, guiSettings.guiSize, guiSettings.guiTitle)
    protected val buttonMap = mutableMapOf<Int, GUIButton>()

    protected var cancelClicks = false

//    fun addButton(slot: Int, button: GUIButton) {
//        buttonMap[slot] = button
//    }

    fun addButton(slot: Int, button: GUIButton) {
        buttonMap[slot] = button
    }

    open fun decorate(player: Player) {
        buttonMap.forEach { (slot, button) -> inventory.setItem(slot, button.iconCreator.apply(player)) }
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = cancelClicks

        val player = event.whoClicked as Player

        val item = event.currentItem ?: return

        val slot = event.slot
        val button = buttonMap[slot] ?: return

        val buttonItem = button.iconCreator.apply(player)

        println("passed the getting test!")

        if (!item.isSimilar(buttonItem)){
            println("item is ${item.type}")
            println("buttonItem is ${buttonItem.type}")
            return
        } // stricter checking

        println("passed the similar test!")

        button.eventConsumer.accept(event)
    }

    override fun onOpen(event: InventoryOpenEvent) {
        decorate(event.player as Player)
    }

    override fun onClose(event: InventoryCloseEvent) { }
}