package dev.jonahm.cellularautomata.data;

import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.events.KeyboardEvent;
import dev.jonahm.cellularautomata.events.MouseEvent;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SimulationFrame extends JFrame {

    public static final int FPS = 120;

    @Getter
    private final MouseEvent mouseEvent;
    @Getter
    private final KeyboardEvent keyboardEvent;

    public SimulationFrame() {
        super("Physics Simulation");
        setPreferredSize(new Dimension(CellularAutomata.WIDTH, CellularAutomata.HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setBackground(new Color(0, 0, 0, 0));
        mouseEvent = new MouseEvent();
        addMouseListener(mouseEvent);
        addMouseMotionListener(mouseEvent);
        keyboardEvent = new KeyboardEvent();
        addKeyListener(keyboardEvent);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::repaint, 1000 / FPS, 1000 / FPS, TimeUnit.MILLISECONDS);
        service.scheduleAtFixedRate(CellularAutomata.getSimulationSpace()::tickGenerationsInLastSecond, 250, 250, TimeUnit.MILLISECONDS);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        mouseEvent.tick();
        CellularAutomata.getRenderManager().renderAll(graphics);
    }
}
