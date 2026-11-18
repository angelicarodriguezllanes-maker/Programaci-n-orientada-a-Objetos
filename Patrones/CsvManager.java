import java.io.IOException;
import java.util.List;

//Orquestador principal que une el parser, el printer y aplica procesadores (DIP)
public class CsvManager {
    private final ICsvParser parser;
    private final ITablePrinter printer;
    private CsvTable currentTable;

    public CsvManager(ICsvParser parser, ITablePrinter printer) {
        this.parser = parser;
        this.printer = printer;
    }

    public void loadFile(String filePath) throws IOException {
        this.currentTable = parser.parse(filePath);
    }

    public CsvTable getTable() {
        ensureTableLoaded();
        return this.currentTable;
    }

    public void applyProcessor(ICsvProcessor processor) {
        ensureTableLoaded();
        processor.process(this.currentTable);
    }

    public void applyProcessors(List<ICsvProcessor> processors) {
        ensureTableLoaded();
        if (processors != null) {
            for (ICsvProcessor p : processors) {
                p.process(this.currentTable);
            }
        }
    }

    public void displayTable() {
        ensureTableLoaded();
        this.printer.print(this.currentTable);
    }

    private void ensureTableLoaded() {
        if (this.currentTable == null) {
            throw new IllegalStateException("No hay ninguna tabla cargada en el gestor.");
        }
    }
}