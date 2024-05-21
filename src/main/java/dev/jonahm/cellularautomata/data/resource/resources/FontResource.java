package dev.jonahm.cellularautomata.data.resource.resources;


import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.resource.Resource;
import dev.jonahm.cellularautomata.data.resource.ResourceType;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FontResource extends Resource {


    public FontResource() {
        super("/font.ttf", ResourceType.FONT);
    }

    public Font getResource() throws IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(CellularAutomata.class.getResourceAsStream(getPath())));
    }

}
