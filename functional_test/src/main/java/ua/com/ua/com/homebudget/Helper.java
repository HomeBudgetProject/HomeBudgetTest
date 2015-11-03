package ua.com.ua.com.homebudget;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.DataProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Anohin Artyom on 03.11.15.
 */
public class Helper {
    String baseEmail = "qatestemail@testdomain.com";
    String basePass= "Qwerty123456";

    public String generateEmail(int local_part, int domain_part, int subdomain){
        String email = "qa" + RandomStringUtils.randomAlphanumeric(local_part - 3) + "@" + RandomStringUtils.randomAlphanumeric(domain_part) + "." + RandomStringUtils.randomAlphanumeric(subdomain) + ".com";
        return email;

    }

    public String generateEmail(int local_part, int domain_part){
        String email = "qa"+ RandomStringUtils.randomAlphanumeric(local_part - 3) + "@"+RandomStringUtils.randomAlphanumeric(domain_part-4)+".com";
        return email;
    }

    public String generatePass(int length){
        String pass =  RandomStringUtils.randomAlphanumeric(length);
        //String pass =  RandomStringUtils.randomAscii(length);
        return pass;
    }

    @DataProvider(name = "dataVerification")
    public Object[][] dataVerification() throws IOException, SAXException, ParserConfigurationException {
        File fXmlFile = new File("dataprovider_verification.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);


        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("account");


        Object[][] array = new Object[nList.getLength()][5];


        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);


            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;


                array[temp][0] = eElement.getElementsByTagName("testnamee").item(0).getTextContent();
                array[temp][1] = eElement.getElementsByTagName("email").item(0).getTextContent();
                array[temp][2] = eElement.getElementsByTagName("password").item(0).getTextContent();
                array[temp][3] = eElement.getElementsByTagName("statuscode").item(0).getTextContent();
                array[temp][4] = eElement.getElementsByTagName("message").item(0).getTextContent();


            }
        }
        return array;
    }
}
