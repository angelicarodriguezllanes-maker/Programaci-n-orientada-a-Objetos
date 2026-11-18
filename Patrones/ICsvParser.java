import java.io.IOException;

//Contrato para el análisis y carga de archivos CSV 
@FunctionalInterface
public interface ICsvParser {
    CsvTable parse(String filePath) throws IOException;
}