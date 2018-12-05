import java.io.*;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;

public class DescriptorFileReader {

    public static DataHolder parseFile() {
        BasicConfigurator.configure();

        try {
            InputStream inputStream = new FileInputStream("/home/hirushi/SynapseUnitTest/src/main/java/DescriptorFile.xml");
            String fileString = FileUtils.readFileToString(new File("/home/hirushi/SynapseUnitTest/src/main/java/DescriptorFile.xml"));
            OMElement xmlFile = AXIOMUtil.stringToOM(fileString);


            //System.out.println(fileString);

            QName qName1 = new QName("", "set-inputXmlPayload", "");
            OMElement inputXmlPayload1 = xmlFile.getFirstChildWithName(qName1);
            String x = inputXmlPayload1.getText();

            QName qName2 = new QName("", "artifactIdString", "");
            OMElement artifactId2 = xmlFile.getFirstChildWithName(qName2);
            String y = artifactId2.getText();

            QName qName3 = new QName("", "fileName", "");
            OMElement fileName1 = xmlFile.getFirstChildWithName(qName3);
            String z = fileName1.getText();

            QName qName4 = new QName("", "properties", "");
            OMElement properties1 = xmlFile.getFirstChildWithName(qName4);
            String w = properties1.getText();

            QName qName5 = new QName("", "expectedPropVal", "");
            OMElement expectedPropVal2 = xmlFile.getFirstChildWithName(qName5);
            String v = expectedPropVal2.getText();

            QName qName6 = new QName("", "expectedPayload", "");
            OMElement expectedPayload2 = xmlFile.getFirstChildWithName(qName6);
            String u = expectedPayload2.getText();

            DataHolder dataHolder = new DataHolder();

           dataHolder.setInputXmlPayload(x);
           dataHolder.setArtifactId(y);
           dataHolder.setFileName(z);
           dataHolder.setProperties(w);
           dataHolder.setExpectedPropVal(v);
           dataHolder.setExpectedPayload(u);

           return dataHolder;


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (XMLStreamException e) {
            System.out.println("Exception 1");
        } catch (IOException e) {
            System.out.println("Exception 2");
        }
        DataHolder dataHolder1 = new DataHolder();
        return dataHolder1;
    }



}