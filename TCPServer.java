package org.wso2.sample5;

import org.json.simple.JSONObject;
import org.wso2.sample5.Agent;
import org.wso2.sample5.Deployer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static ServerSocket server;
    private static int port = 9876;

    /**
     * Method responsible for obtaining data from the client
     */
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        server = new ServerSocket(port);
        while (true) {
            System.out.println("Waiting for client request");
            Socket socket = server.accept();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            JSONObject jsonObject = null;
            jsonObject = (JSONObject) objectInputStream.readObject();
            System.out.println("Message Received: " + jsonObject);


            JSONObject json = new JSONObject(jsonObject);
            String inputXmlPayload = json.get("inputXmlPayload").toString();
            String artifactId = json.get("artifactId").toString();
            String fileName = json.get("fileName").toString();
            String properties = json.get("properties").toString();
            String expectedPropVal = json.get("expectedPropVal").toString();
            String expectedPayload = json.get("expectedPayload").toString();

            try {
                new Deployer().deploy(artifactId, fileName, properties);
                new Agent().createMessageContext(inputXmlPayload);
                new Agent().sequenceMediate(inputXmlPayload);
                String result = new Agent().doAssertions(expectedPayload, expectedPropVal, inputXmlPayload);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("Unit testing result:" + result);

            objectInputStream.close();
            objectInputStream.close();
            socket.close();
        }catch( Exception e){
        }

            if (jsonObject != null) break;
        }
        System.out.println("Shutting down the server!!");

        server.close();
    }
}