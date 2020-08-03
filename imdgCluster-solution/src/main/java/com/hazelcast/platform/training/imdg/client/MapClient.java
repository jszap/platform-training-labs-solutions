package com.hazelcast.platform.training.imdg.client;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Random;

import com.hazelcast.aggregation.Aggregators;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

/**
 * IMDG Cluster exercise 2 - Demonstrate distribute map using C/S deployment topology
 *
 */

public class MapClient {
	
	HazelcastInstance instance;
	
	public MapClient() throws FileNotFoundException{
		//TODO: Create a Hazelcast client instance to the the running 'PRIMARY' cluster
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setClusterName("PRIMARY");
		clientConfig.getNetworkConfig().addAddress("localhost:6701");
		instance =  HazelcastClient.newHazelcastClient(clientConfig);

		Thread mapWriterThread = new Thread(new Runnable(){
			public void run(){
				processInput();
			}
		}); 
		
		mapWriterThread.start();
		
	}
	
	private void processInput(){
		try {
			
			IMap<Object, Object> map=instance.getMap("TrainnigMap");
			Random randInt = new Random();
			Integer.toString(randInt.nextInt(10000));
			
			 while(true){
				String key = "key"+Integer.toString(randInt.nextInt(10000));
				System.out.println("Adding Entry key: "+ key);
		        //String OldValue = map.put(key,Double.toString(Math.random()));
		        map.set(key,Double.toString(Math.random()));
		        
		        getMapEntryCount(map);
		        
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private static long getMapEntryCount(IMap<Object, Object> map) {
        //Execute the aggregation compute and print the result
		//NOTE on a distributed map, this is recommended instead of map.size()
        long entryCount = map.aggregate(Aggregators.<Map.Entry<Object, Object>>count());
        System.out.println("Number of entries in Map<" +map.getName()+">: " + entryCount);
        return entryCount;
	}

	public static void main(String[] args) {
		try {
			MapClient mapClient = new MapClient();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
 }
