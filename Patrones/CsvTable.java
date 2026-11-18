import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//gestiona la estructura de filas y columnas.
public class CsvTable {
    private final List<String> headers;
    private final List<List<String>> rows;

    public CsvTable(List<String> headers, List<List<String>> rows) {
        if (headers == null || headers.isEmpty()) {
            throw new IllegalArgumentException("La tabla debe contener al menos un encabezado.");
        }
        this.headers = new ArrayList<>(headers);
        this.rows = new ArrayList<>();

        if (rows != null) {
            for (List<String> row : rows) {
                this.addRow(row);
            }
        }
    }

    public List<String> getHeaders() {
        return Collections.unmodifiableList(headers);
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public int getRowCount() {
        return rows.size();
    }

    public int getColumnCount() {
        return headers.size();
    }

    public int getColumnIndex(String headerName) {
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).trim().equalsIgnoreCase(headerName.trim())) {
                return i;
            }
        }
        return -1;
    }

    //agrega una nueva fila validando que coincida con el número de columnas.
    public void addRow(List<String> newRow) {
        if (newRow == null || newRow.size() != headers.size()) {
            throw new IllegalArgumentException(
                String.format("Error de dimensión: se esperaban %d columnas, pero se recibieron %d.",
                    headers.size(), (newRow == null ? 0 : newRow.size()))
            );
        }
        this.rows.add(new ArrayList<>(newRow));
    }

    //Permite quitar una fila existente según su índice.
    public void removeRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.size()) {
            throw new IndexOutOfBoundsException("Índice de fila fuera de rango: " + rowIndex);
        }
        this.rows.remove(rowIndex);
    }

    //Agrega una nueva columna con los datos de cada fila.
    public void addColumn(String headerName, List<String> columnData) {
        if (headerName == null || headerName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del encabezado no puede estar vacío.");
        }
        if (getColumnIndex(headerName) != -1) {
            throw new IllegalArgumentException("Ya existe una columna con el nombre: " + headerName);
        }
        if (columnData == null || columnData.size() != rows.size()) {
            throw new IllegalArgumentException(
                String.format("La nueva columna debe tener exactamente %d valores.", rows.size())
            );
        }

        this.headers.add(headerName.trim());
        for (int i = 0; i < rows.size(); i++) {
            this.rows.get(i).add(columnData.get(i));
        }
    }

    // Permite quitar una columna existente por su nombre
    public void removeColumn(String headerName) {
        int colIndex = getColumnIndex(headerName);
        if (colIndex == -1) {
            throw new IllegalArgumentException("No se encontró la columna: " + headerName);
        }
        if (headers.size() == 1) {
            throw new IllegalStateException("No se puede eliminar la única columna restante.");
        }

        this.headers.remove(colIndex);
        for (List<String> row : rows) {
            row.remove(colIndex);
        }
    }
}