import java.io.IOException;
import java.nio.file.*;
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FPC {

    public static void main(String[] args) throws Exception {

        String template_dir = "project_templates/c89_nostdlib_single_header";

        // Define a map for replacements
        Map<String, String> replacements = new LinkedHashMap<>();
        replacements.put("{{OUTPUT_DIR}}", "target");
        replacements.put("{{AUTHOR}}", "nickscha");
        replacements.put("{{YEAR}}", Year.now().toString());
        replacements.put("{{VERSION}}", "v0.1");
        replacements.put("{{LIB_NAME_SHORT}}", "vgg");
        replacements.put("{{LIB_NAME_LONG}}", "Vector Graphics generator");
        replacements.put("{{DESCRIPTION}}", "A pure C89 nostdlib vector graphics generator");

        fpc_replacements_from_user_input(replacements);

        if (replacements.get("{{OUTPUT_DIR}}").equalsIgnoreCase(template_dir)) {
            throw new Exception("[fpc] The target directory cannot be the same as the source directory!");
        }

        replacements.put("{{LIB_NAME_SHORT_LOWER}}", replacements.get("{{LIB_NAME_SHORT}}").toLowerCase());
        replacements.put("{{LIB_NAME_SHORT_UPPER}}", replacements.get("{{LIB_NAME_SHORT}}").toUpperCase());
        replacements.remove("{{LIB_NAME_SHORT}}");

        // Define source and destination directories
        Path sourceDir = Paths.get(template_dir);
        Path destDir = Paths.get(replacements.get("{{OUTPUT_DIR}}"));

        fpc_copy_and_replace(sourceDir, destDir, replacements);
        System.out.println("[fpc] finished");
    }

    private static void fpc_replacements_from_user_input(Map<String, String> replacements) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("[fpc] ###########################################################");
            System.out.println("[fpc] Press Enter to keep the default value or enter a new value.");
            System.out.println("[fpc] ###########################################################");

            // Iterate over a copy of the key set to avoid concurrent modification issues
            for (String key : new LinkedHashMap<>(replacements).keySet()) {

                if (key.equals("{{DESCRIPTION}}")) {
                    replacements.put("{{DESCRIPTION}}",
                            "A C89 standard compliant, single header, nostdlib (no C Standard Library) "
                                    + replacements.get("{{LIB_NAME_LONG}}") + " ("
                                    + replacements.get("{{LIB_NAME_SHORT}}").toUpperCase() + ").");
                }

                String defaultValue = replacements.get(key);

                System.out.printf("[fpc] %-20s [default: %-20s]: ", key, defaultValue);
                String userInput = scanner.nextLine().trim();
                if (!userInput.isEmpty()) {
                    replacements.put(key, userInput);
                }
            }
        }
    }

    private static void fpc_copy_and_replace(Path sourceDir, Path destDir, Map<String, String> replacements)
            throws IOException {
        // Create the destination directory if it doesn't exist
        Files.createDirectories(destDir);

        // Walk the file tree
        Files.walk(sourceDir).forEach(sourcePath -> {
            try {
                Path relativePath = sourceDir.relativize(sourcePath);
                Path destPath = destDir.resolve(relativePath);

                // Handle renaming files that start with "fpc_"
                String fileName = sourcePath.getFileName().toString();
                if (fileName.startsWith("fpc")) {
                    String newFileName = replacements.get("{{LIB_NAME_SHORT_LOWER}}")
                            + fileName.substring("fpc".length());
                    destPath = destPath.getParent().resolve(newFileName);
                }

                if (Files.isDirectory(sourcePath)) {
                    // Create the directory in the destination
                    Files.createDirectories(destPath);
                } else if (Files.isRegularFile(sourcePath)) {
                    // Process and copy the file
                    String content = new String(Files.readAllBytes(sourcePath));
                    String newContent = fpc_replace_placeholders(content, replacements);
                    Files.write(destPath, newContent.getBytes());
                }
            } catch (IOException e) {
                System.err.println("[fpc] Could not copy file " + sourcePath + ": " + e.getMessage());
            }
        });
    }

    private static String fpc_replace_placeholders(String content, Map<String, String> replacements) {
        // Use a simple loop for replacements
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            content = content.replace(entry.getKey(), entry.getValue());
        }
        return content;
    }

}