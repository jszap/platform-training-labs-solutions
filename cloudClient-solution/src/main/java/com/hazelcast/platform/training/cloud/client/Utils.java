package com.hazelcast.platform.training.cloud.client;

import java.util.Properties;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.spi.properties.ClientProperty;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.SSLConfig;

public class Utils {
    //TODO: Set your cluster name here...
    public static String CLOUD_CLUSTER_NAME = "StarterCluster";
	public  static ClientConfig getCloudClientConfig() {
		ClientConfig cloudClientConfig = null;
		try {
	        ClassLoader classLoader = Utils.class.getClassLoader();
	        Properties props = new Properties();
	        
	        //TODO: Sign up for cloud starter and create a cluster. Cut & paste from java client code generated by the cloud service, set the name of your client 'TrainingCloudClinet'
	        // If you choose encryption, copy the store files to the  project resource directory. 
	        props.setProperty("javax.net.ssl.keyStore", classLoader.getResource("client.keystore").toURI().getPath());
	        props.setProperty("javax.net.ssl.keyStorePassword", "daabf90539d");
	        props.setProperty("javax.net.ssl.trustStore", classLoader.getResource("client.truststore").toURI().getPath());
	        props.setProperty("javax.net.ssl.trustStorePassword", "daabf90539d");
	        cloudClientConfig = new ClientConfig();
	        cloudClientConfig.setInstanceName("TrainingCloudClinet");
	        cloudClientConfig.getNetworkConfig().setSSLConfig(new SSLConfig().setEnabled(true).setProperties(props));
	        cloudClientConfig.setGroupConfig(new GroupConfig(CLOUD_CLUSTER_NAME, "4912c2b5a7804ebdbbe898c2b7c311eb"));
	        cloudClientConfig.setProperty("hazelcast.client.statistics.enabled", "true");
	        cloudClientConfig.setProperty(ClientProperty.HAZELCAST_CLOUD_DISCOVERY_TOKEN.getName(), "fd2t3tvPjVg3591dec7IKkZQnP7ZR2YxlvemSTmQit9oRD5v3G");

		}catch(Throwable t) {
			t.printStackTrace();
		}
        return cloudClientConfig;
    }	
}
