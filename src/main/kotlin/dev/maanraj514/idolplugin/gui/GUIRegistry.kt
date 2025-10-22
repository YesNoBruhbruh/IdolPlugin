package dev.maanraj514.idolplugin.gui

import org.bukkit.inventory.Inventory

class GUIRegistry {

    private val registeredGUIs = mutableMapOf<Inventory, GUIHandler>()

    fun registerGUI(inventory: Inventory, guiHandler: GUIHandler) {
        registeredGUIs[inventory] = guiHandler
    }

    fun unregisterGUI(inventory: Inventory) {
        registeredGUIs.remove(inventory)
    }

    fun getHandler(inventory: Inventory): GUIHandler? {
        return registeredGUIs[inventory]
    }

    fun cleanup() {
        registeredGUIs.clear()
    }
}