package dev.jonahm.cellularautomata.events;

import dev.jonahm.cellularautomata.CellularAutomata;
import lombok.Getter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardEvent implements KeyListener {

    @Getter
    private boolean controlPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            CellularAutomata.getSimulationSpace().step();
        }
        if (e.getKeyCode() == KeyEvent.VK_P) {
            CellularAutomata.getRenderManager().getTaskbar().getButtonByName("Play (P)", "Pause (P)").getOnClick().run();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            CellularAutomata.getSimulationSpace().interpolateGrid();
        }
        if (e.getKeyCode() == KeyEvent.VK_C) {
            if (controlPressed) {
                CellularAutomata.getSimulationSpace().copySelection();
            } else {
                CellularAutomata.getSimulationSpace().clear();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_V) {
            if (controlPressed) {
                CellularAutomata.getSimulationSpace().pasteSelection();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlPressed = false;
        }
    }

}
