package dev.maanraj514.idolplugin.task

import dev.maanraj514.idolplugin.idol.IdolManager

class TrustPointsDecayTask(
    private val idolManager: IdolManager,
    private val decayAmount: Int) : Runnable {

    override fun run() {
        for (idolPlayerEntry in idolManager.getIdolPlayers().entries) {
            idolPlayerEntry.value.trust -= decayAmount
        }
    }
}