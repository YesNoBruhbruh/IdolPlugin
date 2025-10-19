package dev.maanraj514.idolplugin.gui.paged.impl

import dev.maanraj514.idolplugin.gui.GUI
import dev.maanraj514.idolplugin.gui.GUISettings
import dev.maanraj514.idolplugin.idol.IdolPlayer
import dev.maanraj514.idolplugin.util.toComponent

class RitualsGUI(idolPlayer: IdolPlayer) : GUI(idolPlayer, GUISettings("<red>Rituals</red>".toComponent(), 9*3)) {
}