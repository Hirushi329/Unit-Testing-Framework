import org.json.simple.JSONObject;

public class JsonConverter {
    public JSONObject convert() {

        JSONObject object = new JSONObject();

        try {
            DescriptorFileReader descriptorFileReader = new DescriptorFileReader();
            DataHolder dataHolder = descriptorFileReader.parseFile();

            object.put("inputXmlPayload", dataHolder.getInputXmlPayload());
            object.put("artifactId", dataHolder.getArtifactId());
            object.put("fileName", dataHolder.getFileName());
            object.put("properties", dataHolder.getProperties());
            object.put("expectedPayload", dataHolder.getExpectedPayload());
            object.put("expectedPropVal", dataHolder.getExpectedPropVal());

            return object;

        } catch (Exception e) {
            e.printStackTrace();
            return  object;
        }

    }
}
