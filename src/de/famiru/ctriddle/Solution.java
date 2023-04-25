package de.famiru.ctriddle;

import java.util.Arrays;
import java.util.List;

public class Solution {
    private final int startX;
    private final int startY;
    private final List<Direction> pathElements;

    public Solution(int startX, int startY, Direction[] pathElements, int length) {
        this.startX = startX;
        this.startY = startY;
        this.pathElements = List.copyOf(Arrays.asList(pathElements).subList(0, length));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Path: (");
        sb.append(startX).append(",").append(startY).append(")");
        for (Direction pathElement : pathElements) {
            sb.append(" -> ")
                    .append(pathElement.name());
        }
        return sb.toString();
    }
}
