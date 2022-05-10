import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException, SAXException, ParseException {
        // CSV to JSON -----------------------------------------------------//
        CSV employee = new CSV("1,John,Smith,USA,25".split(","));
        employee.createCSV(employee.getEmployee(), "staff.csv");

        CSV employee2 = new CSV("2,Inav,Petrov,RU,23".split(","));
        employee2.writeCSV(employee2.getEmployee(), "staff.csv");


        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "staff.csv";

        List<Employee> list = CSV.parseCSV(columnMapping, fileName);

        String json = listToJson(list);

        writeString(json, "data.json");

        // XML to JSON ------------------------------------------------------//

        XML.createXML("1", "John", "Smith", "USA", "25", "staff.xml");
        XML.addInfoToXML("2", "Ivan", "Petrov", "RU", "23");
        List<Employee> list2 = XML.parseXML("staff.xml");

        String jsonXML = listToJson(list2);

        writeString(jsonXML, "data.json");

        // JSON to Object ----------------------------------------------------//

        String json2 = JSON.readString("data2.json");
        List<Employee> list3 = JSON.jsonTolist(json2);
        list3.forEach(System.out::println);
    }

    public static void writeString(String json, String fileName) {

        try (FileWriter file = new FileWriter(fileName)) {
            file.write(json);
            file.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String listToJson(List<Employee> list) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(list, listType);
    }
}

