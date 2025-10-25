package dev.maanraj514.idolplugin.config

import dev.maanraj514.idolplugin.IdolPlugin
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.concurrent.CompletableFuture

abstract class CustomConfig(
    name: String,
    plugin: IdolPlugin) {

    private val file = File(plugin.dataFolder, "$name.yml")
    protected val configuration = YamlConfiguration()

    init {
        if (!file.exists()) {
            CompletableFuture.runAsync {
                configuration.load(file)
            }
        }
    }

    fun save() {
        configuration.save(file)
    }

    fun reload() {
        configuration.load(file)
    }
}