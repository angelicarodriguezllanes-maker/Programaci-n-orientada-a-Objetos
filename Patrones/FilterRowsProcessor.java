import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

//Filtra filas de la tabla según una condición lógica (OCP).
public class FilterRowsProcessor implements ICsvProcessor {
    private final String columnName;
    private final Predicate<String> keepCondition;

    public FilterRowsProcessor(String columnName, Predicate<String> keepCondition) {
        this.columnName = columnName;
        this.keepCondition = keepCondition;
    }

    @Override
    public void process(CsvTable table) {
        int colIndex = table.getColumnIndex(columnName);
        if (colIndex == -1) throw new IllegalArgumentException("Columna no encontrada: " + columnName);

        Iterator<List<String>> iterator = table.getRows().iterator();
        while (iterator.hasNext()) {
            List<String> row = iterator.next();
            if (!keepCondition.test(row.get(colIndex))) {
                iterator.remove();
            }
        }
    }
}