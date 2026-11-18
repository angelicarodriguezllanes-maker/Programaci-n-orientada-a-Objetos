//Contrato fundamental del Patrón Strategy para extender capacidades de procesamiento
@FunctionalInterface
public interface ICsvProcessor {
    void process(CsvTable table);
}