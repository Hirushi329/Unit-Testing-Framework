package org.wso2.sample5;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMDocument;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.synapse.MessageContext;
import org.apache.synapse.config.SynapseConfigUtils;
import org.apache.synapse.config.SynapseConfiguration;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.core.axis2.Axis2SynapseEnvironment;

public class TestUtils {
    public static MessageContext createLightweightSynapseMessageContext(
            String payload) throws Exception{

        return createLightweightSynapseMessageContext(payload, new SynapseConfiguration());
    }

    public static MessageContext createLightweightSynapseMessageContext(
            String payload, SynapseConfiguration config) throws Exception {

        org.apache.axis2.context.MessageContext mc =
                new org.apache.axis2.context.MessageContext();
        SynapseEnvironment env = new Axis2SynapseEnvironment(config);
        MessageContext synMc = new Axis2MessageContext(mc, config, env);
        SOAPEnvelope envelope =
                OMAbstractFactory.getSOAP11Factory().getDefaultEnvelope();
        OMDocument omDoc =
                OMAbstractFactory.getSOAP11Factory().createOMDocument();
        omDoc.addChild(envelope);

        envelope.getBody().addChild(createOMElement(payload));

        synMc.setEnvelope(envelope);
        return synMc;
    }

    public static OMElement createOMElement(String xml) {

        return SynapseConfigUtils.stringToOM(xml);
    }


}