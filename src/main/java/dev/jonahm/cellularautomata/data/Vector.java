package dev.jonahm.cellularautomata.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vector {

    private float x = 0, y = 0;

    public void reverse() {
        x = -x;
        y = -y;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }
}
