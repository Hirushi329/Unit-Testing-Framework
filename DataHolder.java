import org.apache.axiom.om.OMElement;

import java.io.InputStream;

public class DataHolder{

    private String inputXmlPayload;
    private String artifactId;
    private String filename;
    private String properties;
    private String expectedPayload;
    private String expectedPropVal;

    public String getInputXmlPayload(){
        return this.inputXmlPayload; }

    public void setInputXmlPayload(String value ){
        this.inputXmlPayload =  value;
    }

    public String getArtifactId(){
        return this.artifactId;
    }
    public void setArtifactId(String value){
        this.artifactId = value;
    }

    public String getFileName(){
        return this.filename;
    }
    public void setFileName(String value){
        this.filename = value;
    }

    public String getProperties(){ return this.properties; }
    public void setProperties(String value){
        this.properties= value;
    }

    public String getExpectedPayload(){
        return this.expectedPayload;
    }
    public void setExpectedPayload(String value){
        this.expectedPayload = value;
    }

    public String getExpectedPropVal(){
        return this.expectedPropVal;
    }
    public void setExpectedPropVal(String value){
        this.expectedPropVal =value;
    }

    }

