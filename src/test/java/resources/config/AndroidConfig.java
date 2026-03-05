package resources.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class AndroidConfig {

    private static final Properties PROPERTIES = new Properties();
    private static final Path PROPERTIES_PATH = Path.of("src", "test", "java", "resources", "config", "android.properties");

    static {
        loadProperties();
    }

    private AndroidConfig() {
    }

    public static String getRequired(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isBlank()) {
            value = PROPERTIES.getProperty(key);
        }

        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Falta configurar la propiedad obligatoria: " + key);
        }

        return value.trim();
    }

    private static void loadProperties() {
        try (InputStream inputStream = Files.newInputStream(PROPERTIES_PATH)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer configuracion: " + PROPERTIES_PATH, e);
        }
    }
}