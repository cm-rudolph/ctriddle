package de.famiru.ctriddle;

import java.util.ArrayList;
import java.util.List;

public class Riddle {
    private final List<String> rows;
    private char cachedPathChar = '#';
    private List<Path> cachedPaths = new ArrayList<>();
    private String lastNotFoundSubWord = "###";

    public Riddle(List<String> rows) {
        this.rows = rows;

        if (rows == null || rows.isEmpty()) {
            throw new IllegalArgumentException("Rows must not be empty");
        }

        for (String row : rows) {
            if (row.length() != rows.size()) {
                throw new IllegalArgumentException("Riddle must be a square.");
            }
        }
    }

    public Solution find(String word) {
        if (word == null || word.isEmpty()) {
            return null;
        }

        if (!lastNotFoundSubWord.isEmpty() && word.startsWith(lastNotFoundSubWord)) {
            return null;
        }

        if (word.charAt(0) != cachedPathChar) {
            initPathCache(word.charAt(0));
            cachedPathChar = word.charAt(0);
        }


        int longestMatch = 0;
        for (Path path : cachedPaths) {
            Result result = traversePath(path, word, 1);
            if (result.solution != null) {
                lastNotFoundSubWord = "";
                return result.solution;
            } else if (longestMatch < result.longestMatch) {
                longestMatch = result.longestMatch;
                lastNotFoundSubWord = word.substring(0, longestMatch + 1);
            }
        }
        return null;
    }

    private Result traversePath(Path path, String word, int index) {
        if (word.length() == index) {
            // we're done!
            Result result = new Result(index, path.toSolution());
            path.reset();
            return result;
        }
        int longestMatch = index;
        Result bestResult = null;
        for (Direction direction : Direction.values()) {
            if (path.push(direction)) {
                if (rows.get(path.getY()).charAt(path.getX()) == word.charAt(index)) {
                    Result result = traversePath(path, word, index + 1);
                    if (result.solution != null) {
                        return result;
                    } else {
                        if (result.longestMatch > longestMatch) {
                            longestMatch = result.longestMatch;
                            bestResult = result;
                        }
                    }
                }
                path.pop();
            }
        }
        if (bestResult != null) {
            return bestResult;
        }
        return new Result(longestMatch, null);
    }

    private void initPathCache(char c) {
        List<Path> result = new ArrayList<>();
        for (int y = 0; y < rows.size(); y++) {
            String row = rows.get(y);
            for (int x = 0; x < rows.size(); x++) {
                if (row.charAt(x) == c) {
                    result.add(new Path(x, y, rows.size()));
                }
            }
        }
        this.cachedPaths = result;
    }

    record Result(int longestMatch, Solution solution) {
    }
}
