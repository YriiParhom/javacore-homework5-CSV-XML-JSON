import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSV {
    private final String[] employee;

    public CSV(String[] employee) {
        this.employee = employee;
    }


    public String[] getEmployee() {
        return employee;
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileNme) {
        try (CSVReader csvReader = new CSVReader(new FileReader(fileNme))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            return csv.parse();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void createCSV(String[] string, String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeNext(string);
        } catch (IOException ex) {
            System.out.println("Не удалось создать файл");
        }
    }

    public void writeCSV(String[] string, String fileName) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true))) {
            writer.writeNext(string);
        } catch (IOException ex) {
            System.out.println("Не удалось произвести запись файла");
        }
    }
}
