import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class StudentScoreFilter {

    private static final String INPUT_FILE = "studentsEN.txt";
    private static final String OUTPUT_FILE = "studentsENwith5.txt";

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(INPUT_FILE), StandardCharsets.UTF_16));
             FileWriter writer = new FileWriter(OUTPUT_FILE, StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Remove any null characters and normalize whitespace
                String cleaned = line.replace("\u0000", "").trim();
                if (cleaned.isEmpty()) {
                    continue;
                }

                // Split by spaces/tabs and keep only non-empty tokens
                String[] rawParts = cleaned.split("\\s+");
                List<String> tokens = new ArrayList<>();
                for (String token : rawParts) {
                    if (!token.isEmpty()) {
                        tokens.add(token);
                    }
                }

                if (tokens.size() >= 3) {
                    String score = tokens.get(tokens.size() - 1);
                    String name = tokens.get(tokens.size() - 2);
                    // Join any leading parts in case surname has spaces
                    String surname = String.join(" ", tokens.subList(0, tokens.size() - 2));

                    if ("5".equals(score)) {
                        System.out.println("Surname: " + surname + " | Name: " + name + " | Score: " + score);
                        writer.write(surname + " " + name + " " + score + System.lineSeparator());
                    }
                }
            }

            System.out.println("Finished writing students with grade 5 to " + OUTPUT_FILE);

        } catch (IOException e) {
            System.out.println("Error processing student files: " + e.getMessage());
        }
    }
}