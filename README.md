# Librería Extensible de Procesamiento CSV en Java

Librería orientada a objetos desarrollada en **Java** para la lectura, manipulación dinámica, transformación modular y visualización tabular de archivos en formato **CSV (*Comma-Separated Values*)**.

El diseño del proyecto está fundamentado estrictamente en los cinco principios **SOLID** y en los patrones de diseño **Strategy** e **Inyección de Dependencias**, logrando una arquitectura desacoplada, abierta a la extensión y cerrada a la modificación.

---

## 📋 Funcionalidades Principales

* **Carga y Representación Tabular (`ICsvParser` / `SimpleCsvParser`):** Lee un archivo `.csv` desde disco y construye un objeto `CsvTable` que organiza de forma independiente los encabezados (`List<String>`) y los registros (`List<List<String>>`), almacenando todos los valores como cadenas de texto (`String`).
* **Manipulación Estructural Dinámica (`CsvTable`):** Expone métodos seguros para gestionar la estructura en memoria garantizando su integridad dimensional:
  * `addRow(...)`: Inserta nuevas filas.
  * `removeRow(...)`: Elimina registros específicos por índice.
  * `addColumn(...)`: Incorpora columnas completas con sus valores asociados.
  * `removeColumn(...)`: Remueve columnas por nombre.
* **Visualización en Consola (`ITablePrinter` / `ConsoleTablePrinter`):** Formatea y renderiza la tabla en pantalla ajustando dinámicamente el ancho de cada celda y mostrando sus dimensiones totales (filas × columnas).
* **Pipeline de Procesamiento Extensible (`ICsvProcessor` / `CsvManager`):** Permite encadenar múltiples reglas de transformación externas (filtros condicionales, formateo de texto, cálculos matemáticos o expresiones lambda) y ejecutarlas secuencialmente sobre la tabla sin modificar el núcleo de la librería.

---

## 🏗️ Arquitectura, Principios SOLID y Patrones de Diseño

### Aplicación de los Principios SOLID

| Principio | Implementación en la Librería |
| :--- | :--- |
| **S — Responsabilidad Única (SRP)** | Cada clase tiene una única tarea: `CsvTable` gestiona los datos en memoria, `SimpleCsvParser` lee el archivo, `ConsoleTablePrinter` renderiza en consola y `CsvManager` orquesta el flujo. |
| **O — Abierto/Cerrado (OCP)** | El núcleo (`CsvTable` y `CsvManager`) no se modifica al añadir nuevas reglas de negocio; se extienden comportamientos implementando `ICsvProcessor` (ej. `FilterRowsProcessor`, `UppercaseColumnProcessor`, `TaxCalculatorProcessor`). |
| **L — Sustitución de Liskov (LSP)** | Cualquier implementación concreta de `ICsvProcessor` (clases dedicadas o expresiones lambda) puede intercambiarse dentro de `List<ICsvProcessor>` sin alterar el comportamiento esperado de `CsvManager`. |
| **I — Segregación de Interfaces (ISP)** | Contratos pequeños y cohesivos: `ICsvParser` (`parse`), `ITablePrinter` (`print`) e `ICsvProcessor` (`process`). |
| **D — Inversión de Dependencias (DIP)** | `CsvManager` depende únicamente de las abstracciones `ICsvParser` e `ITablePrinter`, recibiendo sus implementaciones desde el exterior. |

### Patrones de Diseño Empleados

1. **Patrón Strategy (Comportamiento):** Encapsula los algoritmos de procesamiento (`ICsvProcessor`), análisis sintáctico (`ICsvParser`) y renderizado (`ITablePrinter`) en familias intercambiables, eliminando condicionales complejos (`if-else` / `switch`) en el orquestador.
2. **Inyección de Dependencias (Estructural):** El constructor `public CsvManager(ICsvParser parser, ITablePrinter printer)` recibe las dependencias concretas desde la clase cliente (`Main`), reduciendo el acoplamiento.

---

## 🧪 Validación y Flujo de Ejecución (`Main`)

El funcionamiento de la librería se valida mediante un caso de prueba dividido en tres etapas:

1. **Carga y Visualización Inicial (`1. TABLA ORIGINAL`):** Lectura del archivo CSV base y renderizado en consola (**4 filas × 4 columnas**).
2. **Manipulación Estructural (`2. TABLA TRAS MODIFICAR FILAS Y COLUMNAS`):**
   * Inserción del registro `105 | Monitor 27p | 300.00 | Norte`.
   * Incorporación de la columna `Sucursal`.
   * Eliminación de la primera fila (`ID 101`) y de la columna `Categoria`.
3. **Procesamiento Extensible (`3. TABLA FINAL TRAS PROCESAMIENTO EXTENSIBLE`):** Ejecución en pipeline de tres procesadores y una expresión lambda:
   * Filtrado de productos con precio mayor a `100.00`.
   * Conversión de los nombres de productos a mayúsculas.
   * Cálculo matemático del 16 % de IVA (`Precio_Con_IVA`).
   * Prefijado de sucursales con `"SUC-"` mediante expresión lambda.

**Resultado final:** Tabla consolidada de **3 filas × 5 columnas** ejecutada sin errores.

---

## 👥 Autoras

* **Angelica Rodriguez** — C. I. 32.220.574
* **Rebecca Saras** — C. I. 32.039.073

**Institución:** Universidad Rafael Urdaneta — Facultad de Ingeniería  
**Asignatura:** Programación Orientada a Objetos  
**Repositorio:** [Programación Orientada a Objetos](https://github.com/angelicarodriguezllanes-maker/Programaci-n-orientada-a-Objetos.git)
