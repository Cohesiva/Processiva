package com.cohesiva.processes.db.jbpm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import org.drools.persistence.info.WorkItemInfo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class WorkItemInfoDao {
	@PersistenceUnit(unitName="jbpmWebAppPU")
	private EntityManagerFactory emf;

	@Transactional
	public List<WorkItemInfo> getPersistedWorkItems() {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Query query = em.createNativeQuery(
				"Select * from workiteminfo where name != 'Human Task'",
			WorkItemInfo.class);
		
		List<WorkItemInfo> list = query.getResultList();
		
		tx.commit();

		return list;
	}
}
