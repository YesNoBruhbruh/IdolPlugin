package dev.maanraj514.idolplugin.idol

import dev.maanraj514.idolplugin.gui.GUIService
import dev.maanraj514.idolplugin.gui.paged.impl.RitualsGUI
import dev.maanraj514.idolplugin.gui.paged.impl.WishesGUI
import dev.maanraj514.idolplugin.util.Cuboid
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Idol(
    val name: String,
    val cuboid: Cuboid,
    // maybe could have made an abstraction DonationItem -> Implementation..
    private val guiService: GUIService,
    private val donationToPoints: MutableMap<Material, Int>,
    //TODO implementation of rituals
    private val wishToPoints: MutableMap<Material, Int>) {

    init {
        //purely for visuals
        cuboid.display()
    }

    // also factor in how many of that specific item was donated, and multiply.
    fun onDonation(item: Item, idolPlayer: IdolPlayer): Boolean {
        if (!canDonate(item.location)) return false

        val itemStack = item.itemStack
        item.remove() // to show visually it got accepted

        handleDonation(itemStack, idolPlayer)

        // if not active anymore, totally fine, we just need
        // to send a message anyway
        val player = Bukkit.getPlayer(idolPlayer.uuid) ?: return true
        player.sendMessage("Your trustPoints have been updated to ${idolPlayer.trust}")

        return true
    }

    private fun handleDonation(itemStack: ItemStack, idolPlayer: IdolPlayer) {
        val material = itemStack.type
        val isWish = material != Material.BUNDLE

        if (isWish) {
            val amount = itemStack.amount
            // the -1 is for deduction for wrong offering,
            // but make sure to allow for configurability in end product.
            val trustPoints = donationToPoints.getOrDefault(material, -1) * amount
            idolPlayer.trust += trustPoints

            guiService.openGUI(idolPlayer, WishesGUI(idolPlayer))
            return
        }

        // this means it is a ritual.

        guiService.openGUI(idolPlayer, RitualsGUI(idolPlayer))
    }

    fun canDonate(itemLocation: Location): Boolean {
        return cuboid.inRegion(itemLocation)
    }

    fun addDonationFilter(material: Material, trustPoints: Int): Boolean {
        if (isAlreadyFiltered(material)) return false

        donationToPoints[material] = trustPoints
        return true
    }

    fun removeDonationFilter(material: Material): Boolean {
        if (!isAlreadyFiltered(material)) return false // doesn't exist

        donationToPoints.remove(material)
        return true
    }

    fun sendDonationFilterMessage(player: Player) {
        if (donationToPoints.isEmpty()){
            player.sendMessage("No filter yet!")
            return
        }

        player.sendMessage("==========DONATION FILTER==========")

        for (filterEntry in donationToPoints.entries) {
            val material = filterEntry.key
            val trustPoints = filterEntry.value

            player.sendMessage("${material.name.uppercase()} -> $trustPoints")
        }

        player.sendMessage("===================================")
    }

    fun isAlreadyFiltered(material: Material): Boolean {
        return donationToPoints.containsKey(material)
    }
}