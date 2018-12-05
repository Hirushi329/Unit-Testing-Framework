package org.wso2.sample5;

import org.apache.synapse.MessageContext;
import org.apache.synapse.mediators.base.SequenceMediator;



public class Agent {
    /**
     * Create message context from the inputXmlPayload
     */
    public MessageContext createMessageContext(String inputXmlPayload) {
        MessageContext msgCtxt = null;

        try {
            System.out.println("=============================================Building msgCtxt=================================================");
            msgCtxt = TestUtils.createLightweightSynapseMessageContext(inputXmlPayload);
            System.out.println("msgCtxt is build" + msgCtxt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return msgCtxt;
    }

    /**
     * Mediate the message through the given sequence
     */
    public void sequenceMediate(String inputXmlPayload) {
        System.out.println("=======================================Mediating the message through the sequence======================================");
        SequenceMediator sequenceMediator = new SequenceMediator();
        Boolean result = sequenceMediator.mediate(createMessageContext(inputXmlPayload));
        System.out.println("=====================================Mediation Result:" + result + "=======================================");
    }

    public String doAssertions(String expectedPayload, String expectedPropVal, String inputXmlPayload){
        String key = "envelope";

        String format = "<p:echoInt xmlns:p=\"http://echo.services.core.carbon.wso2.org\">"
                + "<in>1</in></p:echoInt>";

        MessageContext msgCtxt = createMessageContext(inputXmlPayload);

        boolean result1 = (expectedPropVal.equals(msgCtxt.getEnvelope().toString()));
        System.out.println("=================================Property Value Assertion Result:" + result1 + "======================================");

        boolean result2 = (expectedPayload.equals(msgCtxt.getEnvelope().getBody().toString()));
        System.out.println("======================================Payload Assertion Result:" + result2 + "=====================================");

        if(result1 == true && result2 == true){
            String message = "Unit Testing is Successful";
            System.out.println("==============================" + message + "===============================");
            return message;
        }

        else{
            String message = "Unit testing failed";
            System.out.println("===============================" + message + "===============================");
            return message;
        }


    }
}
