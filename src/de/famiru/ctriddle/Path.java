package de.famiru.ctriddle;

import static de.famiru.ctriddle.Direction.*;

public class Path {
    private final int startX;
    private final int startY;
    private final Direction[] pathElements;
    private final boolean[] used;
    private final int width;
    private int index;
    private int x;
    private int y;


    public Path(int startX, int startY, int width) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be positive.");
        }
        if (startX >= width || startY >= width || startX < 0 || startY < 0) {
            throw new IllegalArgumentException("startX or startY out of bounds. Must be between 0 and " + width);
        }
        this.width = width;
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.pathElements = new Direction[width * width];
        this.used = new boolean[width * width];
        this.index = 0;
        markUsed(startX, startY);
    }

    public boolean push(Direction direction) {
        if (index == pathElements.length) {
            return false;
        }
        if (isInvalidDirection(direction)) {
            return false;
        }

        int nextX = getNextX(direction);
        int nextY = getNextY(direction);

        if (isAlreadyUsed(nextX, nextY)) {
            return false;
        }
        markUsed(nextX, nextY);

        pathElements[index] = direction;
        this.x = nextX;
        this.y = nextY;
        index++;
        return true;
    }

    private boolean isInvalidDirection(Direction direction) {
        if (index == 0) {
            return false;
        }
        return direction == opposite(pathElements[index - 1]);
    }

    private boolean isAlreadyUsed(int x, int y) {
        return used[x * width + y];
    }

    private void markUsed(int x, int y) {
        used[x * width + y] = true;
    }

    private void markUnused(int x, int y) {
        used[x * width + y] = false;
    }

    private int getNextX(Direction direction) {
        if (direction == UP || direction == DOWN) {
            return x;
        }
        if (direction == LEFT) {
            if (x == 0) {
                return width - 1;
            }
            return x - 1;
        }
        if (direction == RIGHT) {
            if (x == width - 1) {
                return 0;
            }
            return x + 1;
        }
        throw new IllegalStateException();
    }

    private int getNextY(Direction direction) {
        if (direction == DOWN) {
            if (y == width - 1) {
                return 0;
            }
            return y + 1;
        }
        if (direction == UP) {
            if (y == 0) {
                return width - 1;
            }
            return y - 1;
        }
        if (direction == LEFT || direction == RIGHT) {
            return y;
        }
        throw new IllegalStateException();
    }

    public void pop() {
        if (index == 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        index--;
        markUnused(x, y);
        Direction direction = opposite(pathElements[index]);
        x = getNextX(direction);
        y = getNextY(direction);
    }

    public void reset() {
        while (index > 0) {
            pop();
        }
    }

    private Direction opposite(Direction direction) {
        return switch (direction) {
            case LEFT -> RIGHT;
            case UP -> DOWN;
            case RIGHT -> LEFT;
            case DOWN -> UP;
        };
    }

    public int length() {
        return index + 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Solution toSolution() {
        return new Solution(startX, startY, pathElements, index);
    }
}
