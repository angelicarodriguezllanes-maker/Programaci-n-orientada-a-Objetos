import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//Lee archivos CSV separados por comas 
public class SimpleCsvParser implements ICsvParser {
    private final String delimiter;

    public SimpleCsvParser() {
        this(",");
    }

    public SimpleCsvParser(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public CsvTable parse(String filePath) throws IOException {
        List<String> headers = new ArrayList<>();
        List<List<String>> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                throw new IOException("El archivo CSV está vacío: " + filePath);
            }

            String[] rawHeaders = line.split(delimiter, -1);
            for (String h : rawHeaders) {
                headers.add(h.trim());
            }

            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] rawValues = line.split(delimiter, -1);
                if (rawValues.length != headers.size()) {
                    throw new IOException(String.format("Error en línea %d: se esperaban %d columnas, hay %d.",
                            lineNumber, headers.size(), rawValues.length));
                }

                List<String> row = new ArrayList<>();
                for (String val : rawValues) {
                    row.add(val.trim());
                }
                rows.add(row);
            }
        }
        return new CsvTable(headers, rows);
    }
}