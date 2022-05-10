
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSON {

    public static List<Employee> jsonTolist(String json) throws ParseException {
        List<Employee> list = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Object obj = new JSONParser().parse(json);
        JSONArray jsonArray = (JSONArray) obj;
        for (Object array : jsonArray) {
            list.add(gson.fromJson(array.toString(), Employee.class));
        }
        return list;
    }

    public static String readString(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                return s;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
