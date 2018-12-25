/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.SynapseUnitTestAgent;

import org.apache.log4j.Logger;

/**
 * Class responsible for receiving test data and and maintaining the test flow
 */
public class Agent {

    private static Logger log = Logger.getLogger(Agent.class.getName());
    private TCPServer tcpServer = new TCPServer();

    public void initialize() {

        TCPServer tcpServer = new TCPServer();
        Agent agent = new Agent();
        tcpServer.readData(agent);
    }

    public void executeTest(String message) {

        try {
            log.info("Message received:" + message);

            if (message.startsWith("|") && message.endsWith("|")) {
                String operation = MessageFormatUtils.getOperation(message);
                log.info(operation);

                if (operation.equals("deploy")) {
                    String artifact = MessageFormatUtils.getDeploymentData(message);
                    String result = new Deployer().deploy(artifact);

                    if (result != null) {
                        String deploymentResult = "Sequence is deployed successfully";
                        log.info("Sequence deployed successfully");
                        String messageToBeSent = MessageFormatUtils.generateResultMessage(deploymentResult);
                        log.info(messageToBeSent);
                        tcpServer.writeData(messageToBeSent);

                    } else
                        log.error("Sequence not deployed");

                } else if (operation.equals("executeTest")) {
                    log.info("Test data received unit testing begins");
                    String[] testDataValues = MessageFormatUtils.getTestData(message);
                    String inputXmlPayload = testDataValues[0];
                    String expectedPayload = testDataValues[1];
                    String expectedPropVal = testDataValues[2];
                    log.info(inputXmlPayload);
                    log.info(expectedPayload);
                    log.info(expectedPropVal);

                    new TestExecutor().sequenceMediate(inputXmlPayload);
                    String unitTestResult = new TestExecutor().doAssertions(expectedPayload, expectedPropVal, inputXmlPayload);
                    String resultToBeSent = MessageFormatUtils.generateResultMessage(unitTestResult);
                    log.info(resultToBeSent);
                    tcpServer.writeData(resultToBeSent);

                } else
                    log.error("Operation not identified");
            } else
                log.error("This is not a valid message");

        } catch (Exception e) {
            log.info("IOException");
            tcpServer.writeData("IOException");
        }
    }
}