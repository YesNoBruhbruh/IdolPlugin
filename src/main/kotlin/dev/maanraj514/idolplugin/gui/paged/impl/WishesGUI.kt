package dev.maanraj514.idolplugin.gui.paged.impl

import dev.maanraj514.idolplugin.gui.GUIButton
import dev.maanraj514.idolplugin.gui.GUISettings
import dev.maanraj514.idolplugin.gui.paged.PagedGUI
import dev.maanraj514.idolplugin.idol.Idol
import dev.maanraj514.idolplugin.idol.IdolPlayer
import dev.maanraj514.idolplugin.util.ItemBuilder
import dev.maanraj514.idolplugin.util.toComponent
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class WishesGUI(idolPlayer: IdolPlayer, idol: Idol) : PagedGUI(idolPlayer, GUISettings("<gold>Wishes!</gold>".toComponent(), 54)) {

    init {
        guiSettings.guiTitle = "<gold><bold>Current points: ${idolPlayer.trust}</bold></gold>".toComponent()

        cancelClicks = true

        var i=0
        for (wishEntry in idol.wishToPoints.entries) {
            val wishMaterial = wishEntry.key
            val wishPointsCost = wishEntry.value

            println("wishEntry = ${wishMaterial.name}, $wishPointsCost")

            val wishButton = GUIButton(
                {
                    ItemBuilder(wishMaterial)
                        .lore(listOf(Component.text("trustCost: $wishPointsCost")))
                        .build()
                }, {
                    val player = it.whoClicked as Player

                    if (idolPlayer.trust < wishPointsCost) {
                        player.sendMessage("<red>You do not have enough trust!</red>".toComponent())
                        return@GUIButton
                    }

                    if (!idol.isWishSuccess(wishMaterial, idolPlayer)){
                        player.sendMessage("<red>Your wish has been unfulfilled!</red>".toComponent())
                        return@GUIButton
                    }

                    player.inventory.addItem(ItemStack(wishMaterial))
                    player.sendMessage("<green>You have redeemed your wish!</green>".toComponent())

                    player.sendMessage("<aqua>Your trust has been updated to ${idolPlayer.trust}</aqua>".toComponent())
                })
            addButton(i, wishButton)
            i++
        }
    }
}