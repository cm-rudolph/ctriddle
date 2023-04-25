package de.famiru.ctriddle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Runner {
    private final Riddle riddle;

    public Runner() {
        List<String> rows = List.of("QYURPAVEETUPMPRA",
                "RETEUMIRREHCIETW",
                "PKCUTRNOHDTNSSZD",
                "SRURNOFIEATENCHO",
                "BDIEENETBSLIEALI",
                "ERKHTOHPTUCDGNLN",
                "AKKRDASFRNASOMSN",
                "BZWETPNDAWLSDECA",
                "ETKRORLNMEDUOMRL",
                "NEMITPLUSLOCLEER",
                "DNONAPERAWDAMTIB",
                "MAATSVACLGORINAL",
                "TBTSRIAYCDPSRKRI",
                "ARUUPTLPCCABEDEE",
                "TINAIIKSIEDYCHBT",
                "UBRMNTREDLSCOCYD");

        this.riddle = new Riddle(rows);;
    }

    public static void main(String[] args) {
        Runner runner = new Runner();
        // zgrep -aE '^[a-zA-Z]{11}[a-zA-Z]*$' crackstation.txt.gz | tr '[:lower:]' '[:upper:]' | uniq > crackstation.11plus.txt
        runner.tryAllWords("crackstation.11plus.txt");
    }

    private void tryAllWords(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Solution solution = riddle.find(line);
                if (solution != null) {
                    System.out.println(line + " (" + line.length() + "): " + solution);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
