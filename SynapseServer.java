import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.lang.System;

public class SynapseServer {
    /**
     * This is the agent class to startup a synapse server instance, build message context and to deploy sequences
     */
    private static final String DEFAULT_SYNAPSE_HOME_LOCATION = ".";
    public static final String INTEGRATION_SYNAPSE_XML = "integration-synapse.xml";


    private static final Log log = LogFactory.getLog(SynapseServer.class);

    private Process process;

    @GET
    @Path("/start")
    public synchronized void startServer() {

        if (process == null) {
            try {
                String synapseHomeLocation = getSynapseHome();

                File synapseHome = Paths.get(synapseHomeLocation).toFile();

                String[] cmdArray;
                // For Windows
                if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                    cmdArray = new String[]{"cmd.exe", "/c", synapseHomeLocation + File.separator + "bin" + File.separator +
                            "synapse.bat", "-synapseConfig", synapseHomeLocation + File.separator + "repository"
                            + File.separator + "conf" + File.separator + INTEGRATION_SYNAPSE_XML};
                } else {
                    // For Unix
                    cmdArray = new String[]{"sh", synapseHomeLocation + File.separator + "bin" + File.separator +
                            "synapse.sh", "-synapseConfig", synapseHomeLocation + File.separator + "repository"
                            + File.separator + "conf" + File.separator + INTEGRATION_SYNAPSE_XML};
                }

                process = Runtime.getRuntime().exec(cmdArray, null, synapseHome);

            } catch (Exception ex) {
                log.error("Error while starting synapse server", ex);
            }
        }

    }

    private String getSynapseHome() {

        return System.getProperty("synapse.home", DEFAULT_SYNAPSE_HOME_LOCATION);
    }

    @GET
    @Path("/stop")
    public synchronized void stopServer() {
        if (process != null) {
            try {
                String synapseKillCommand = getSynapseHome() + File.separator + "bin" + File.separator + "synapse-stop.sh";
                Runtime.getRuntime().exec(synapseKillCommand);
            } catch (IOException e) {
                log.error("Error while stopping synapse server", e);
            }
            process = null;
        }
    }

}
