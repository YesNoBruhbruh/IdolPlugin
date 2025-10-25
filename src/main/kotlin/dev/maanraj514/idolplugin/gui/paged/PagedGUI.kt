package dev.maanraj514.idolplugin.gui.paged

import dev.maanraj514.idolplugin.gui.GUI
import dev.maanraj514.idolplugin.gui.GUIButton
import dev.maanraj514.idolplugin.gui.GUISettings
import dev.maanraj514.idolplugin.idol.IdolPlayer
import dev.maanraj514.idolplugin.util.ItemBuilder
import dev.maanraj514.idolplugin.util.toComponent
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import kotlin.math.ceil

abstract class PagedGUI(idolPlayer: IdolPlayer, guiSettings: GUISettings) : GUI(idolPlayer, guiSettings) {

    private val slots = mutableListOf<Int>()
    private val paginatedButtons = mutableMapOf<Int, GUIButton>()
    private var usableSlots = 0
    protected var currentPage = 0

    protected var nextPageButton = GUIButton({
        ItemBuilder(Material.GREEN_CONCRETE)
            .name("<green>Next Page!</green>".toComponent())
            .build()
    }, {
        nextPage(it.whoClicked as Player)
    })
    protected var backPageButton = GUIButton({
        ItemBuilder(Material.RED_CONCRETE)
            .name("<red>Back Page!</red>".toComponent())
            .build()
    }, {
        backPage(it.whoClicked as Player)
    })

    init {
        cancelClicks = true
    }

    override fun decorate(player: Player) {
        decorate(player, 1)
    }

    private fun decorate(player: Player, page: Int) {
        inventory.clear()
        addMenuBorder(player)
        currentPage = page

        val buttons = buttonMap.values.toList()
        val amount = buttons.size

        val start = (page-1)*usableSlots
        val end = (page*usableSlots) - 1

        var index = 0

        // i is for the slot, index is for the button
        for (i in 0 until usableSlots) {
            val result = start + index
            if (index > end || amount <= result) continue

            val button = buttons[result]
            inventory.setItem(slots[i], button.iconCreator.apply(player))
            paginatedButtons[slots[i]] = button
            index++
        }
    }

    fun nextPage(player: Player) {
        val pages = getPagesRequired()
        if ((currentPage - 1) == pages) return //can't go to next page

        decorate(player, currentPage+1)
    }

    fun backPage(player: Player) {
        if (currentPage == 1) return

        decorate(player, currentPage-1)
    }

    private fun getPagesRequired(): Int {
        val buttonsSize = buttonMap.size
        val slotsSize = slots.size
        val result = (buttonsSize/slotsSize).toDouble()
        val res = ceil(result).toInt()

        if (res < 1) return 1

        return res
    }

    private fun addMenuBorder(player: Player) {
        inventory.setItem(48, backPageButton.iconCreator.apply(player))
        inventory.setItem(50, nextPageButton.iconCreator.apply(player))
        paginatedButtons[48] = backPageButton
        paginatedButtons[50] = nextPageButton

        val fillerItem = ItemBuilder(Material.BLACK_STAINED_GLASS_PANE)
            .name(Component.empty())
            .build()

        for (i in 0..9) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillerItem)
            }
        }

        inventory.setItem(17, fillerItem)
        inventory.setItem(18, fillerItem)
        inventory.setItem(26, fillerItem)
        inventory.setItem(27, fillerItem)
        inventory.setItem(35, fillerItem)
        inventory.setItem(36, fillerItem)

        for (i in 44 until 54) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, fillerItem)
            }
        }
    }

    // do the event handling
    // for the clicking event, might have to do pdc for every item storing their slot.

    override fun onOpen(event: InventoryOpenEvent) {
        val player = event.player as Player

        addMenuBorder(player)

        for (i in 0 until inventory.size) {
            val item = inventory.getItem(i)
            if (item == null || item.type == Material.AIR) {
                slots.add(i)
            }
        }

        usableSlots = slots.size

        decorate(player)
    }

    override fun onClick(event: InventoryClickEvent) {
        event.isCancelled = cancelClicks

        handleButton(event, paginatedButtons)
    }
}