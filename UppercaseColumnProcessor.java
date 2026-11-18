import java.util.List;

/**
 * Procesador personalizado creado por el usuario (Extensibilidad / OCP).
 */
public class UppercaseColumnProcessor implements ICsvProcessor {
    private final String targetColumn;

    public UppercaseColumnProcessor(String targetColumn) {
        this.targetColumn = targetColumn;
    }

    @Override
    public void process(CsvTable table) {
        int colIndex = table.getColumnIndex(targetColumn);
        if (colIndex == -1) throw new IllegalArgumentException("Columna no encontrada: " + targetColumn);

        for (List<String> row : table.getRows()) {
            String val = row.get(colIndex);
            if (val != null) row.set(colIndex, val.toUpperCase());
        }
    }
}