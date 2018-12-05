package org.wso2.sample5;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.description.Parameter;
import org.apache.axis2.engine.AxisConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.config.SynapseConfiguration;
import org.apache.synapse.core.SynapseEnvironment;
import org.apache.synapse.core.axis2.Axis2SynapseEnvironment;
import org.apache.synapse.deployers.SequenceDeployer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.Properties;

/**
 * Util class for deploying synapse artifacts
 */

public class Deployer {
    private static final Logger logger = LoggerFactory.getLogger(Deployer.class);

    public void deploy(String artifactIdString, String fileName, String properties) throws Exception {
        final Log log = LogFactory.getLog(Deployer.class);
        logger.info("========================================================Deploying Sequence=====================================================");

        Properties propertiesObject = new Properties();
        propertiesObject.load(new StringReader(properties));

        OMElement inputElement = AXIOMUtil.stringToOM(artifactIdString);
        SequenceDeployer sequenceDeployer = new SequenceDeployer();

        SynapseConfiguration synapseConfiguration = new SynapseConfiguration();
        AxisConfiguration axisConfiguration = synapseConfiguration.getAxisConfiguration();
        ConfigurationContext cfgCtx = new ConfigurationContext(axisConfiguration);
        SynapseEnvironment synapseEnvironment = new Axis2SynapseEnvironment(cfgCtx, synapseConfiguration);
        axisConfiguration.addParameter(new Parameter(SynapseConstants.SYNAPSE_ENV, synapseEnvironment));
        axisConfiguration.addParameter(new Parameter(SynapseConstants.SYNAPSE_CONFIG, synapseConfiguration));
        cfgCtx.setAxisConfiguration(axisConfiguration);

        sequenceDeployer.init(cfgCtx);
        String response = sequenceDeployer.deploySynapseArtifact(inputElement, fileName, propertiesObject);
        System.out.println("======================Sequence Mediate:" + response + "=====================");
    }
}
