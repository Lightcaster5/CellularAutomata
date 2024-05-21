package dev.jonahm.cellularautomata.data.resource.resources;


import dev.jonahm.cellularautomata.CellularAutomata;
import dev.jonahm.cellularautomata.data.resource.Resource;
import dev.jonahm.cellularautomata.data.resource.ResourceType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageResource extends Resource {


    public ImageResource(String path, ResourceType resourceType) {
        super(path, resourceType);
    }

    public BufferedImage getResource() throws IOException {
        return ImageIO.read(Objects.requireNonNull(CellularAutomata.class.getResourceAsStream(getPath())));
    }

}
