package com.cohesiva.processes.db.jbpm;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.jbpm.persistence.processinstance.ProcessInstanceInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ProcessInstanceInfoDao {
	@PersistenceUnit(unitName = "jbpmWebAppPU")
	private EntityManagerFactory emf;

	@Transactional
	public List<ProcessInstanceInfo> getRunningInstances(String processId) {
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Query query = em.createNativeQuery(
				"Select * from processinstanceinfo where processid = '"
						+ processId + "'", ProcessInstanceInfo.class);

		List<ProcessInstanceInfo> list = query.getResultList();

		tx.commit();

		return list;
	}

	@Transactional
	public List<Long> getRunningInstancesIds() {
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Query query = em
				.createNativeQuery("Select instanceid from processinstanceinfo");

		List<BigInteger> list = query.getResultList();

		List<Long> result = new ArrayList<Long>();

		for (BigInteger bigInt : list) {
			result.add(bigInt.longValue());
		}

		tx.commit();

		return result;
	}
}
