
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class XML {

    public static List<Employee> parseXML(String fileName) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));


        NodeList nodeList = doc.getElementsByTagName("employee");

        List<Employee> empoloyeeList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element employeeElement = (Element) nodeList.item(i);

                Employee employee = new Employee();

                NodeList childNode = employeeElement.getChildNodes();
                for (int j = 0; j < childNode.getLength(); j++) {
                    if (childNode.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        Element childElements = (Element) childNode.item(j);

                        switch (childElements.getNodeName()) {
                            case "id" -> employee.setId(Long.parseLong(childElements.getTextContent()));
                            case "firstName" -> employee.setFirstName(childElements.getTextContent());
                            case "lastName" -> employee.setLastName(childElements.getTextContent());
                            case "country" -> employee.setCoutry(childElements.getTextContent());
                            case "age" -> employee.setAge(Integer.parseInt(childElements.getTextContent()));
                        }
                    }
                }
                empoloyeeList.add(employee);
            }
        }
        return empoloyeeList;
    }

    public static void addInfoToXML(String idText, String firstNameText, String lastNameText,
                                    String coutryText, String ageText) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse("staff.xml");

        Element nList = document.getDocumentElement();

        Element employee = document.createElement("employee");
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(idText));
        employee.appendChild(id);
        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode(firstNameText));
        employee.appendChild(firstName);
        Element lastName = document.createElement("lastName");
        lastName.appendChild(document.createTextNode(lastNameText));
        employee.appendChild(lastName);
        Element country = document.createElement("country");
        country.appendChild(document.createTextNode(coutryText));
        employee.appendChild(country);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode(ageText));
        employee.appendChild(age);

        nList.appendChild(employee);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(new File("staff.xml"));
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }

    public static void createXML(String idText, String firstNameText, String lastNameText, String coutryText,
                                 String ageText, String fileName) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element staff = document.createElement("staff");
            document.appendChild(staff);
            Element employee = document.createElement("employee");
            staff.appendChild(employee);
            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(idText));
            employee.appendChild(id);
            Element firstName = document.createElement("firstName");
            firstName.appendChild(document.createTextNode(firstNameText));
            employee.appendChild(firstName);
            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(lastNameText));
            employee.appendChild(lastName);
            Element country = document.createElement("country");
            country.appendChild(document.createTextNode(coutryText));
            employee.appendChild(country);
            Element age = document.createElement("age");
            age.appendChild(document.createTextNode(ageText));
            employee.appendChild(age);

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(fileName));
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException | DOMException ex) {
            ex.printStackTrace();
        }
    }
}
