package work.eip.monitor.eureka;

import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Sample Eureka service that registers with Eureka to receive and process requests.
 * This example just receives one request and exits once it receives the request after processing it.
 *
 */
public class EurekaClientStandaloneApplication {

	private static ApplicationInfoManager applicationInfoManager;
	private static EurekaClient eurekaClient;

	private static synchronized ApplicationInfoManager initializeApplicationInfoManager(EurekaInstanceConfig instanceConfig) {
		if (applicationInfoManager == null) {
			InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig).get();
			applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
		}

		return applicationInfoManager;
	}

	private static synchronized EurekaClient initializeEurekaClient(ApplicationInfoManager applicationInfoManager, EurekaClientConfig clientConfig) {
		if (eurekaClient == null) {
			eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
		}

		return eurekaClient;
	}


	public static void main(String[] args) {

		DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
		ApplicationInfoManager applicationInfoManager = initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
		EurekaClient eurekaClient = initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

		Map<String, String> map = new HashMap();
		map.put("management.port", configInstance.getStringProperty("eureka.management.port", "9913").getValue());
		applicationInfoManager.registerAppMetadata(map);

		EurekaServiceBase exampleServiceBase = new EurekaServiceBase(applicationInfoManager, eurekaClient, configInstance);
		try {
			exampleServiceBase.start();
		} finally {
			// the stop calls shutdown on eurekaClient
			exampleServiceBase.stop();
		}
//		exampleServiceBase.stop();
	}

}