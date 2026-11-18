import java.util.ArrayList;
import java.util.List;

/**
 * Agrega una nueva columna calculando impuestos a partir de una existente (OCP).
 */
public class TaxCalculatorProcessor implements ICsvProcessor {
    private final String baseColumn;
    private final String newColumnName;
    private final double taxRate;

    public TaxCalculatorProcessor(String baseColumn, String newColumnName, double taxRate) {
        this.baseColumn = baseColumn;
        this.newColumnName = newColumnName;
        this.taxRate = taxRate;
    }

    @Override
    public void process(CsvTable table) {
        int colIndex = table.getColumnIndex(baseColumn);
        if (colIndex == -1) throw new IllegalArgumentException("Columna base no encontrada: " + baseColumn);

        List<String> calculatedValues = new ArrayList<>();
        for (List<String> row : table.getRows()) {
            double base = Double.parseDouble(row.get(colIndex));
            double total = base * (1.0 + taxRate);
            calculatedValues.add(String.format("%.2f", total));
        }
        table.addColumn(newColumnName, calculatedValues);
    }
}