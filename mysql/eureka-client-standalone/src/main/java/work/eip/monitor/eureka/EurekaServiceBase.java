package work.eip.monitor.eureka;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.EurekaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * An example service (that can be initialized in a variety of ways) that registers with eureka
 * and listens for REST calls on port 8001.
 */
public class EurekaServiceBase {

    private final ApplicationInfoManager applicationInfoManager;
    private final EurekaClient eurekaClient;
    private final DynamicPropertyFactory configInstance;

    private final static Logger logger = LoggerFactory.getLogger(EurekaServiceBase.class);

    public EurekaServiceBase(ApplicationInfoManager applicationInfoManager,
                             EurekaClient eurekaClient,
                             DynamicPropertyFactory configInstance) {
        this.applicationInfoManager = applicationInfoManager;
        this.eurekaClient = eurekaClient;
        this.configInstance = configInstance;
    }

    @PostConstruct
    public void start() {
        // A good practice is to register as STARTING and only change status to UP
        // after the service is ready to receive traffic
        logger.info("Registering service to eureka with STARTING status");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);

        logger.info("Simulating service initialization by sleeping for 10 seconds...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // Nothing
        }

        // Now we change our status to UP
        logger.info("Done sleeping, now changing status to UP");
        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
//        waitForRegistrationWithEureka(eurekaClient);
        logger.info("Service started and ready to process requests..");

         try {
//             int myServingPort = applicationInfoManager.getInfo().getPort();  // read from my registered info
             int myServingPort = 9914;
             ServerSocket serverSocket = new ServerSocket(myServingPort);
             final Socket s = serverSocket.accept();
             logger.info("Client got connected... processing request from the client");
             processRequest(s);

         } catch (IOException e) {
             e.printStackTrace();
         }

        logger.info("Simulating service doing work by sleeping for " + 5 + " seconds...");
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            // Nothing
        }
    }

    @PreDestroy
    public void stop() {
        if (eurekaClient != null) {
            logger.info("Shutting down server. Demo over.");
            eurekaClient.shutdown();
        }
    }

//    private void waitForRegistrationWithEureka(EurekaClient eurekaClient) {
//        // my vip address to listen on
//        String vipAddress = configInstance.getStringProperty("eureka.vipAddress", "sampleservice.mydomain.net").get();
//        InstanceInfo nextServerInfo = null;
//        while (nextServerInfo == null) {
//            try {
//                nextServerInfo = eurekaClient.getNextServerFromEureka(vipAddress, false);
//            } catch (Throwable e) {
//                logger.info("Waiting ... verifying service registration with eureka ...");
//
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//    }

    private void processRequest(final Socket s) {
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line = rd.readLine();
            if (line != null) {
                logger.info("Received a request from the example client: " + line);
            }
            String response = "BAR " + new Date();
            logger.info("Sending the response to the client: " + response);

            PrintStream out = new PrintStream(s.getOutputStream());
            out.println(response);

        } catch (Throwable e) {
            System.err.println("Error processing requests");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}