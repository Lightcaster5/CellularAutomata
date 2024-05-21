package dev.jonahm.cellularautomata.data.rendercomponent.impl;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.Colors;
import dev.jonahm.cellularautomata.data.SimulationFrame;
import dev.jonahm.cellularautomata.data.SimulationState;
import dev.jonahm.cellularautomata.data.rendercomponent.RenderComponent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Arrays;

import static dev.jonahm.cellularautomata.CellularAutomata.CELL_SIZE;

public class SimulationSpace implements RenderComponent {

    @Getter
    @Setter
    private SimulationState simulationState = SimulationState.PAUSED;

    @Getter
    private boolean grid = false, selectionSet = false;
    @Getter
    private int generation = 0;

    private final float[][] space = new float[(CellularAutomata.HEIGHT - Navbar.HEIGHT - Taskbar.HEIGHT) / CELL_SIZE][CellularAutomata.WIDTH / CELL_SIZE];
    private float[][] copiedSpace;

    private final int[] generationsInLastSecondCache = new int[10];

    private int generationTick = 0, generationsInLastSecond = 0, hoveredCellRow, hoveredCellCol, selectionStartRow, selectionStartCol, selectionEndRow, selectionEndCol;

    public SimulationSpace() {
        set(25, 10, 1f);
        set(26, 11, 1f);
        set(24, 12, 1f);
        set(25, 12, 1f);
        set(26, 12, 1f);
    }

    @Override
    public int getRenderPriority() {
        return 5;
    }

    @Override
    public void render(Graphics2D graphics) {

        graphics.setColor(Colors.MAIN_BG.get());
        graphics.fillRect(0, 0, graphics.getClipBounds().width, graphics.getClipBounds().height);

        if (grid) {
            for (int col = 1; col < getColumnSize(); col++) {
                graphics.setColor(hoveredCellCol == col || hoveredCellCol == col - 1 ? Colors.GRID_HOVERED_BG.get() : getColumnSize() / 2 == col ? Colors.GRID_CENTER_BG.get() : Colors.GRID_BG.get());
                graphics.fillRect(0, col * CELL_SIZE + Navbar.HEIGHT, graphics.getClipBounds().width, 1);
            }
            for (int row = 1; row < getRowSize(); row++) {
                graphics.setColor(hoveredCellRow == row || hoveredCellRow == row - 1 ? Colors.GRID_HOVERED_BG.get() : getRowSize() / 2 == row ? Colors.GRID_CENTER_BG.get() : Colors.GRID_BG.get());
                graphics.fillRect(row * CELL_SIZE, Navbar.HEIGHT, 1, graphics.getClipBounds().height - Navbar.HEIGHT - Taskbar.HEIGHT);
            }
        }

        for (int col = 0; col < getColumnSize(); col++) {
            for (int row = 0; row < getRowSize(); row++) {
                float value = space[col][row];
                graphics.setColor(new Color(1f, 1f, 1f, value));
                graphics.fillRect(row * CELL_SIZE, col * CELL_SIZE + Navbar.HEIGHT, CELL_SIZE, CELL_SIZE);
            }
        }

        if (CellularAutomata.getSimulationFrame() != null && CellularAutomata.getSimulationFrame().getMouseEvent().isSelecting()) {
            int x = selectionStartRow * CELL_SIZE;
            int y = selectionStartCol * CELL_SIZE;
            int width = (hoveredCellRow + 1) * CELL_SIZE - x - (hoveredCellRow == getRowSize() - 1 ? 1 : 0);
            int height = (hoveredCellCol + 1) * CELL_SIZE - y;
            graphics.setColor(Colors.SELECTION_BG.get());
            graphics.fillRect(x, y + Navbar.HEIGHT, width, height);
            graphics.setColor(Colors.SELECTION_FG.get());
            graphics.drawRect(x, y + Navbar.HEIGHT, width, height);
        }

        if (selectionSet) {
            int x = selectionStartRow * CELL_SIZE;
            int y = selectionStartCol * CELL_SIZE;
            int width = selectionEndRow * CELL_SIZE - x - (selectionEndRow == getRowSize() ? 1 : 0);
            int height = selectionEndCol * CELL_SIZE - y;
            graphics.setColor(Colors.SELECTION_BG.get());
            graphics.fillRect(x, y + Navbar.HEIGHT, width, height);
            graphics.setColor(Colors.SELECTION_FG.get());
            graphics.drawRect(x, y + Navbar.HEIGHT, width, height);
        }

        if (copiedSpace != null && hoveredCellRow != -1 && hoveredCellCol != -1) {
            for (int col = selectionStartCol; col < selectionEndCol; col++) {
                for (int row = selectionStartRow; row < selectionEndRow; row++) {
                    float value = copiedSpace[col - selectionStartCol][row - selectionStartRow];
                    int x = col - selectionStartCol + hoveredCellCol + 1;
                    int y = row - selectionStartRow + hoveredCellRow + 1;
                    if (x >= 0 && x < getColumnSize() && y >= 0 && y < getRowSize()) {
                        graphics.setColor(new Color(1f, 1f, 1f, value));
                        graphics.fillRect(y * CELL_SIZE, x * CELL_SIZE + Navbar.HEIGHT, CELL_SIZE, CELL_SIZE);
                    }
                }
            }

            int x = (hoveredCellRow + 1) * CELL_SIZE;
            int y = (hoveredCellCol + 1) * CELL_SIZE;
            int width = ((selectionEndRow - selectionStartRow) + hoveredCellRow + 1) * CELL_SIZE - x - (((selectionEndRow - selectionStartRow) + hoveredCellRow) == getRowSize() - 1 ? 1 : 0);
            int height = ((selectionEndCol - selectionStartCol) + hoveredCellCol + 1) * CELL_SIZE - y;

            if (height + y > CellularAutomata.HEIGHT - Taskbar.HEIGHT - Navbar.HEIGHT) {
                height = (CellularAutomata.HEIGHT - Taskbar.HEIGHT - Navbar.HEIGHT) - y;
            }

            graphics.setColor(Colors.SELECTION_BG.get());
            graphics.fillRect(x, y + Navbar.HEIGHT, width, height);
            graphics.setColor(Colors.SELECTION_FG.get());
            graphics.drawRect(x, y + Navbar.HEIGHT, width, height);
        }

        if (simulationState == SimulationState.RUNNING) {
            if (generationTick < SimulationFrame.FPS / CellularAutomata.SIMULATION_SPEED) {
                generationTick++;
                return;
            }
            generationTick = 0;
            step();
        }

    }

    public void step() {
        float[][] cachedSpace = new float[getColumnSize()][getRowSize()];
        for (int col = 0; col < getColumnSize(); col++) {
            for (int row = 0; row < getRowSize(); row++) {
                cachedSpace[col][row] = space[col][row];
            }
        }
        for (int col = 0; col < getColumnSize(); col++) {
            for (int row = 0; row < getRowSize(); row++) {
                int surroundingAlive = 0;
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        if (i == 0 && j == 0) continue;
                        int colD = col + i, rowD = row + j;
                        if (colD < 0) {
                            colD = getColumnSize() - 1;
                        } else if (colD >= getColumnSize()) {
                            colD = 0;
                        }
                        if (rowD < 0) {
                            rowD = getRowSize() - 1;
                        } else if (rowD >= getRowSize()) {
                            rowD = 0;
                        }
                        surroundingAlive += space[colD][rowD] == 1 ? 1 : 0;

                    }
                }
                if (surroundingAlive < 2 || surroundingAlive > 3) {
                    cachedSpace[col][row] = 0;
                } else if (surroundingAlive == 3) {
                    cachedSpace[col][row] = 1;
                }
            }
        }
        for (int col = 0; col < getColumnSize(); col++) {
            for (int row = 0; row < getRowSize(); row++) {
                space[col][row] = cachedSpace[col][row];
            }
        }
        generation++;
        generationsInLastSecond++;
    }

    public int getColumnSize() {
        return space.length;
    }

    public int getRowSize() {
        return space[0].length;
    }

    public void set(int x, int y, float value) {
        space[y][x] = value;
    }

    public float get(int x, int y) {
        return space[y][x];
    }

    public void setHoveredCell(int x, int y) {
        hoveredCellRow = y;
        hoveredCellCol = x;
    }

    public void setSelectionStart(int x, int y) {
        selectionStartRow = y;
        selectionStartCol = x;
        unsetSelection();
    }

    public void setSelection() {
        selectionSet = true;
        selectionEndRow = hoveredCellRow + 1;
        selectionEndCol = hoveredCellCol + 1;
    }

    public void unsetSelection() {
        selectionSet = false;
    }

    public void copySelection() {
        if (selectionSet) {
            copiedSpace = new float[selectionEndCol - selectionStartCol][selectionEndRow - selectionStartRow];
            for (int col = selectionStartCol; col < selectionEndCol; col++) {
                for (int row = selectionStartRow; row < selectionEndRow; row++) {
                    copiedSpace[col - selectionStartCol][row - selectionStartRow] = space[col][row];
                }
            }
            unsetSelection();
        }
    }

    public boolean isCopiedSpaceSet() {
        return copiedSpace != null;
    }

    public void clearCopiedSpace() {
        copiedSpace = null;
    }

    public void pasteSelection() {
        if (copiedSpace != null) {
            for (int col = selectionStartCol; col < selectionEndCol; col++) {
                for (int row = selectionStartRow; row < selectionEndRow; row++) {
                    float value = copiedSpace[col - selectionStartCol][row - selectionStartRow];
                    int y = col - selectionStartCol + hoveredCellCol + 1;
                    int x = row - selectionStartRow + hoveredCellRow + 1;
                    if (x >= 0 && x < getRowSize() && y >= 0 && y < getColumnSize()) {
                        space[y][x] = value;
                    }
                }
            }
        }
    }

    public void clear() {
        for (int col = 0; col < getColumnSize(); col++) {
            for (int row = 0; row < getRowSize(); row++) {
                space[col][row] = 0f;
            }
        }
        generation = generationTick = generationsInLastSecond = 0;
        unsetSelection();
        clearCopiedSpace();
    }

    public void interpolateSimulationStateState() {
        if (getSimulationState().equals(SimulationState.PAUSED)) setSimulationState(SimulationState.RUNNING);
        else setSimulationState(SimulationState.PAUSED);
    }

    public void interpolateGrid() {
        grid = !grid;
    }

    public void tickGenerationsInLastSecond() {
        for (int i = 0; i < generationsInLastSecondCache.length - 1; i++) {
            generationsInLastSecondCache[i] = generationsInLastSecondCache[i + 1];
        }
        generationsInLastSecondCache[generationsInLastSecondCache.length - 1] = generationsInLastSecond;
        generationsInLastSecond = 0;
    }

    public String getGenerationsInLastSecondAvg() {
        double avg = (double) Arrays.stream(generationsInLastSecondCache).sum() / generationsInLastSecondCache.length * 4;
        return String.format("%.2f", avg);
    }
}