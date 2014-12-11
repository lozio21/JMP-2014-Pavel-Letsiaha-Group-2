package com.epam.jmp.mbean;

import java.lang.management.ManagementFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Singleton
@Startup
public class MonitoringApp implements MonitoringAppMXBean {

	@PersistenceContext
	EntityManager em;

	private MBeanServer platformMBeanServer;
	private ObjectName objectName;

	@PostConstruct
	public void registerInJMX() {
		try {
			objectName = new ObjectName("com.epam.jmp.mbean:type="
					+ this.getClass().getSimpleName());
			platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
			platformMBeanServer.registerMBean(this, objectName);
		} catch (Exception e) {
			throw new IllegalStateException("Error during registration MBean:"
					+ e);
		}
	}

	@Override
	public Long getNumberOfStudents() {
		Query query = em.createQuery("SELECT count(s) from Student s");
		Long numberOfStudents = (Long) query.getSingleResult();
		return numberOfStudents;
	}

	@Override
	public String getLongestAddress() {
		Query query = em
				.createQuery("SELECT s.address from Student s ORDER BY LENGTH(s.address) DESC").setMaxResults(1);
		String longestAddress = (String) query.getSingleResult();
		return longestAddress;
	}

	@PreDestroy
	public void unregisterFromJMX() {
		try {
			platformMBeanServer.unregisterMBean(this.objectName);
		} catch (Exception e) {
			throw new IllegalStateException(
					"Error during unregistration MBean:" + e);
		}
	}
}