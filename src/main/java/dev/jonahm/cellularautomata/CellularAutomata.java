package dev.jonahm.cellularautomata;

import dev.jonahm.cellularautomata.data.SimulationFrame;
import dev.jonahm.cellularautomata.data.rendercomponent.impl.SimulationSpace;
import dev.jonahm.cellularautomata.managers.RenderManager;
import dev.jonahm.cellularautomata.managers.ResourceManager;
import dev.jonahm.cellularautomata.utils.Threading;
import lombok.Getter;

import java.awt.*;

public class CellularAutomata {

    public static final int WIDTH = 1200, HEIGHT = 800, CELL_SIZE = 10, SIMULATION_SPEED = 50; // speed is gens/second

    @Getter
    private static CellularAutomata instance;

    @Getter
    private static SimulationFrame simulationFrame;
    @Getter
    private static SimulationSpace simulationSpace;
    @Getter
    private static RenderManager renderManager;
    @Getter
    private static ResourceManager resourceManager;

    public CellularAutomata() {
        instance = this;
    }

    public void start() {
        Threading.runAsync(() -> resourceManager = new ResourceManager());

        simulationSpace = new SimulationSpace();
        renderManager = new RenderManager();
        simulationFrame = new SimulationFrame();
    }

    public Font getFont() {
        if (resourceManager != null) return resourceManager.getFont();
        return new Font("Arial", Font.PLAIN, 16);
    }

    public static void main(String[] args) {
        CellularAutomata cellularAutomata = new CellularAutomata();
        cellularAutomata.start();
    }

}
