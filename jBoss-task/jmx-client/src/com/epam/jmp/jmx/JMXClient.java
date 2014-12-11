package com.epam.jmp.jmx;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXClient {

	public static void main(String[] args) {

		try (JMXConnector jmxConnector = JMXConnectorFactory.connect(
				new JMXServiceURL(
						"service:jmx:remoting-jmx://epbyminw1941:9999"), null);) {
			MBeanServerConnection mbeanConn = jmxConnector
					.getMBeanServerConnection();
			for (;;) {
				CompositeDataSupport memory = (CompositeDataSupport) mbeanConn
						.getAttribute(new ObjectName("java.lang:type=Memory"),
								"HeapMemoryUsage");
				Long memoryUsed = (Long) memory.get("used") / 1000000;

				Integer liveThreads = (Integer) mbeanConn.getAttribute(
						new ObjectName("java.lang:type=Threading"),
						"ThreadCount");

				ObjectName objectName = new ObjectName(
						"com.epam.jmp.mbean:type=MonitoringApp");

				Long numberOfStudents = (Long) mbeanConn.getAttribute(
						objectName, "NumberOfStudents");

				String longestAddress = (String) mbeanConn.getAttribute(
						objectName, "LongestAddress");

				System.out.println("Memory Used: " + memoryUsed
						+ "\nLive Threads: " + liveThreads
						+ "\nNumber of Students: " + numberOfStudents
						+ "\nLongest address: " + longestAddress + "\n");

				Thread.sleep(10000);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}