package dev.jonahm.cellularautomata.events;


import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.Navbar;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.SimulationSpace;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.Taskbar;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class MouseEvent implements MouseListener, MouseMotionListener {

    @Getter
    private boolean clicking = false, inWindow = false, clickingNav = false, selecting = false;
    private int x, y;

    private final List<String> positionsChanged = new ArrayList<>();

    public void tick() {
        if ((clicking && inWindow) || clickingNav) {
            if (y <= Navbar.HEIGHT) {
                int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo.getPointerInfo().getLocation().y;
                CellularAutomata.getSimulationFrame().setLocation(x - this.x, y - this.y);
            } else if (clicking && inWindow) {
                SimulationSpace simulation = CellularAutomata.getSimulationSpace();
                int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo.getPointerInfo().getLocation().y;
                x = x - CellularAutomata.getSimulationFrame().getX();
                y = y - CellularAutomata.getSimulationFrame().getY() - Navbar.HEIGHT;
                int col = x / CellularAutomata.CELL_SIZE, row = y / CellularAutomata.CELL_SIZE;
                if (col >= 0 && col < simulation.getRowSize() && row >= 0 && row < simulation.getColumnSize()) {
                    if (!positionsChanged.contains(col + ":" + row) && !selecting && !CellularAutomata.getSimulationSpace().isSelectionSet() && !CellularAutomata.getSimulationSpace().isCopiedSpaceSet()) {
                        positionsChanged.add(col + ":" + row);
                        CellularAutomata.getSimulationSpace().set(col, row, simulation.get(col, row) == 1 ? 0 : 1f);
                    }
                    simulation.setHoveredCell(row, col);
                }

            }
        }
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        if (e.getY() >= CellularAutomata.HEIGHT - Taskbar.HEIGHT) { // clicked in taskbar
            CellularAutomata.getRenderManager().getTaskbar().onClick(e.getPoint());
        }
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        clicking = true;
        x = e.getX();
        y = e.getY();
        if (y <= Navbar.HEIGHT) clickingNav = true;
        SimulationSpace simulation = CellularAutomata.getSimulationSpace();
        if (CellularAutomata.getSimulationFrame().getKeyboardEvent().isControlPressed()) {
            int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo.getPointerInfo().getLocation().y;
            x = x - CellularAutomata.getSimulationFrame().getX();
            y = y - CellularAutomata.getSimulationFrame().getY() - Navbar.HEIGHT;
            int col = x / CellularAutomata.CELL_SIZE;
            int row = y / CellularAutomata.CELL_SIZE;
            if (col >= 0 && col < simulation.getRowSize() && row >= 0 && row < simulation.getColumnSize()) {
                selecting = true;
                CellularAutomata.getSimulationSpace().setSelectionStart(row, col);
            }
        }
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        CellularAutomata.getSimulationSpace().clearCopiedSpace();
        if (selecting) {
            CellularAutomata.getSimulationSpace().setSelection();
        } else {
            CellularAutomata.getSimulationSpace().unsetSelection();
        }
        clicking = clickingNav = selecting = false;
        positionsChanged.clear();
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        inWindow = true;
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        inWindow = false;
        CellularAutomata.getSimulationSpace().setHoveredCell(-1, -1);
    }

    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {

    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
        SimulationSpace simulation = CellularAutomata.getSimulationSpace();
        int col = -1, row = -1;
        if (e.getY() >= CellularAutomata.HEIGHT - Taskbar.HEIGHT) { // moved in taskbar
            CellularAutomata.getRenderManager().getTaskbar().onHover(e.getPoint());
        } else if (e.getY() > Navbar.HEIGHT) {
            int x = MouseInfo.getPointerInfo().getLocation().x, y = MouseInfo.getPointerInfo().getLocation().y;
            x = x - CellularAutomata.getSimulationFrame().getX();
            y = y - CellularAutomata.getSimulationFrame().getY() - Navbar.HEIGHT;
            col = x / CellularAutomata.CELL_SIZE;
            row = y / CellularAutomata.CELL_SIZE;
            if (col >= 0 && col < simulation.getRowSize() && row >= 0 && row < simulation.getColumnSize()) {
                simulation.setHoveredCell(row, col);
            }
        }
        simulation.setHoveredCell(row, col);
    }
}
