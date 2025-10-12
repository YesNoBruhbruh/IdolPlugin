package dev.maanraj514.idolplugin

import dev.maanraj514.idolplugin.idol.IdolManager
import org.bukkit.plugin.java.JavaPlugin

class IdolPlugin : JavaPlugin() {

    private lateinit var idolManager: IdolManager

    override fun onEnable() {
        idolManager = IdolManager()
    }

    override fun onDisable() {
    }
}