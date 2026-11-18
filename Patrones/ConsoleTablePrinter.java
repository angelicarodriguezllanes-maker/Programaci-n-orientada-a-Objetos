import java.util.List;

//Imprime la tabla formateada en la consola con anchos de columna dinámicos.
public class ConsoleTablePrinter implements ITablePrinter {
    @Override
    public void print(CsvTable table) {
        if (table == null) {
            System.out.println("[Tabla nula]");
            return;
        }

        List<String> headers = table.getHeaders();
        List<List<String>> rows = table.getRows();
        int[] colWidths = calculateColumnWidths(table);
        String separator = buildSeparator(colWidths);

        System.out.println(separator);
        printRow(headers, colWidths);
        System.out.println(separator);

        if (rows.isEmpty()) {
            System.out.println("| (Sin registros) ");
        } else {
            for (List<String> row : rows) {
                printRow(row, colWidths);
            }
        }
        System.out.println(separator);
        System.out.printf("Total: %d filas x %d columnas%n%n", rows.size(), headers.size());
    }

    private int[] calculateColumnWidths(CsvTable table) {
        List<String> headers = table.getHeaders();
        int[] widths = new int[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            widths[i] = headers.get(i).length();
        }
        for (List<String> row : table.getRows()) {
            for (int i = 0; i < row.size(); i++) {
                String cell = row.get(i) != null ? row.get(i) : "";
                if (cell.length() > widths[i]) widths[i] = cell.length();
            }
        }
        return widths;
    }

    private String buildSeparator(int[] widths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : widths) {
            sb.append("-".repeat(w + 2)).append("+");
        }
        return sb.toString();
    }

    private void printRow(List<String> cells, int[] widths) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < cells.size(); i++) {
            String val = cells.get(i) != null ? cells.get(i) : "";
            sb.append(" ").append(String.format("%-" + widths[i] + "s", val)).append(" |");
        }
        System.out.println(sb);
    }
}