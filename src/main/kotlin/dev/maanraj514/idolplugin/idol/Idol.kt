package dev.maanraj514.idolplugin.idol

import dev.maanraj514.idolplugin.gui.GUIService
import dev.maanraj514.idolplugin.gui.impl.WishesGUI
import dev.maanraj514.idolplugin.idol.filter.impl.DonationHandler
import dev.maanraj514.idolplugin.idol.filter.impl.RitualHandler
import dev.maanraj514.idolplugin.idol.filter.impl.WishingHandler
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
    private val guiService: GUIService) {

    private val donationHandler = DonationHandler()
    private val ritualHandler = RitualHandler()
    private val wishingItems = WishingHandler()

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
            val trustPoints = donationHandler.getIdolItemPoints(itemStack)
            idolPlayer.trust += trustPoints

            guiService.openGUI(idolPlayer, WishesGUI(idolPlayer))
            return
        }

        // this means it is a ritual.


    }

    fun canDonate(itemLocation: Location): Boolean {
        return cuboid.inRegion(itemLocation)
    }

    fun addDonationFilter(material: Material, trustPoints: Int): Boolean {
        if (isAlreadyFiltered(material)) return false

        donationFilter[material] = trustPoints
        return true
    }

    fun removeDonationFilter(material: Material): Boolean {
        if (!isAlreadyFiltered(material)) return false // doesn't exist

        donationFilter.remove(material)
        return true
    }

    fun sendDonationFilterMessage(player: Player) {
        if (donationFilter.isEmpty()){
            player.sendMessage("No filter yet!")
            return
        }

        player.sendMessage("==========DONATION FILTER==========")

        for (filterEntry in donationFilter.entries) {
            val material = filterEntry.key
            val trustPoints = filterEntry.value

            player.sendMessage("${material.name.uppercase()} -> $trustPoints")
        }

        player.sendMessage("===================================")
    }

    fun isAlreadyFiltered(material: Material): Boolean {
        return donationFilter.containsKey(material)
    }
}