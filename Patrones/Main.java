import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Clase principal que ejecuta y prueba todo el sistema.
 */
public class Main {
    public static void main(String[] args) {
        String sampleCsvPath = "ventas_ejemplo.csv";

        try {
            // Creamos un archivo CSV físico de prueba
            createSampleCsvFile(sampleCsvPath);

            // Inyección de dependencias iniciales
            ICsvParser parser = new SimpleCsvParser(",");
            ITablePrinter printer = new ConsoleTablePrinter();
            CsvManager manager = new CsvManager(parser, printer);

            // Cargar archivo
            manager.loadFile(sampleCsvPath);
            System.out.println("=== 1. TABLA ORIGINAL ===");
            manager.displayTable();

            // Manipular datos nativos de la tabla
            CsvTable table = manager.getTable();
            table.addRow(Arrays.asList("105", "Monitor 27p", "Electronica", "300.00"));
            table.addColumn("Sucursal", Arrays.asList("Norte", "Sur", "Norte", "Centro", "Norte"));
            table.removeRow(0); // Elimina ID 101
            table.removeColumn("Categoria");

            System.out.println("=== 2. TABLA TRAS MODIFICAR FILAS Y COLUMNAS ===");
            manager.displayTable();

            // Aplicar procesadores externos (Extensibilidad sin modificar la librería)
            List<ICsvProcessor> pipeline = Arrays.asList(
                new FilterRowsProcessor("Precio", price -> Double.parseDouble(price) > 100.0),
                new UppercaseColumnProcessor("Producto"),
                new TaxCalculatorProcessor("Precio", "Precio_Con_IVA", 0.16),
                t -> { // Procesador rápido mediante expresión lambda
                    int idx = t.getColumnIndex("Sucursal");
                    for (List<String> r : t.getRows()) {
                        r.set(idx, "SUC-" + r.get(idx).toUpperCase());
                    }
                }
            );

            manager.applyProcessors(pipeline);
            System.out.println("=== 3. TABLA FINAL TRAS PROCESAMIENTO EXTENSIBLE ===");
            manager.displayTable();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Files.deleteIfExists(Path.of(sampleCsvPath));
            } catch (IOException ignored) {}
        }
    }

    private static void createSampleCsvFile(String path) throws IOException {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write("ID,Producto,Categoria,Precio\n");
            writer.write("101,Teclado Mecanico,Perifericos,85.00\n");
            writer.write("102,Laptop Pro 15,Computadores,1200.00\n");
            writer.write("103,Mouse Inalambrico,Perifericos,45.50\n");
            writer.write("104,Silla Ergonomica,Oficina,250.00\n");
        }
    }
}