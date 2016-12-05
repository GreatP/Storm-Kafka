package cn.ac.sict.ljc.main;

import java.io.IOException;
import java.util.Properties;

import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;

import cn.ac.sict.ljc.sensorSimu_terminal.SensorTopology;

/**
 * storm 提交时指定此 Main 类为入口, 则运行所有配置了的拓扑
 */
public class Main {

	public static void main(String[] args)
			throws AlreadyAliveException, InvalidTopologyException, AuthorizationException {

		Properties configProps = new Properties();
		try {
			configProps.load(Main.class.getClassLoader().getResourceAsStream("sysConfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR 1: no Config file.");
			return;
		}

		// 配置 cn.ac.sict.ljc.demo.WordCountTopology
		SensorTopology sensorTopology = new SensorTopology(configProps);
		sensorTopology.submit(configProps.getProperty("topologyName_ljc_sensor"));

	}
}
