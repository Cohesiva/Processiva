/*
 * #%L
 * Processiva Business Processes Platform
 * %%
 * Copyright (C) 2012 Cohesiva
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package com.cohesiva.processes.jbpm.serviceImpl.base;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.persistence.info.WorkItemInfo;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.process.instance.WorkItem;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.WorkItemManager;
import org.jbpm.persistence.processinstance.ProcessInstanceInfo;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.process.workitem.email.EmailWorkItemHandler;
import org.jbpm.process.workitem.wsht.MinaHTWorkItemHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.db.jbpm.ProcessInstanceInfoDao;
import com.cohesiva.processes.db.jbpm.WorkItemInfoDao;
import com.cohesiva.processes.jbpm.handlers.BaseAsynchronousWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.GetSpreadsheetValueWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.AddSubscriberWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.BalanceInquiryWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.BasketWeeklySummaryWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.HandleNewPlayerWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.LoadPlayersListsWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.MakeBasketPaymentWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.MakePaymentsWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.basket.RemoveSubscriberWorkItemHandler;
import com.cohesiva.processes.jbpm.handlers.hr.getCVWorkItemHandler;
import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.base.ISessionManager;
import com.cohesiva.processes.jbpm.service.processes.basket.IBasketProcessesService;

@Service
public class JbpmBase implements IJbpmBase {

	private StatefulKnowledgeSession ksession = null;

	private JPAWorkingMemoryDbLogger logger = null;

	private MinaHTWorkItemHandler humanTaskHandler = null;

	@Autowired
	private ISessionManager sessionManager;

	@Autowired
	private WorkItemInfoDao workItemInfoDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProcessInstanceInfoDao processInstanceInfoDao;
	
	@Autowired
	private EmailWorkItemHandler emailWorkItemHandler;
	
	@Autowired
	private IBasketProcessesService basketProcessServie;

	// Injected database connection:
	@PersistenceUnit(unitName = "jbpmPU")
	private EntityManagerFactory emf;

	private final String WORK_ITEM_HANDLER_CLASS_SUFFIX = "WorkItemHandler";
	private String[] workItemHandlersPackages = {
			"com.cohesiva.processes.jbpm.handlers.basket",
			"com.cohesiva.processes.jbpm.handlers" };

	// Service initialization method - load jbpm session
	@PostConstruct
	public void init() {
		try {
			if (ksession == null) {

				System.out.println("DEFOLT CZARSET: "+Charset.defaultCharset());
				// load up the knowledge base
				KnowledgeBase kbase = readKnowledgeBase();

				// create knowledge session to interact with the engine
				ksession = createKnowledgeSession(kbase);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void destroy() {
		if (humanTaskHandler != null) {
			try {
				humanTaskHandler.dispose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (logger != null) {
			logger.dispose();
		}
		if (ksession != null) {
			ksession.dispose();
		}
	}

	public StatefulKnowledgeSession getSession() {
		return ksession;
	}

	private KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		
		kbuilder.add(ResourceFactory
				.newClassPathResource("jbpm/basket/basketWeekly.bpmn"),
				ResourceType.BPMN2);
		kbuilder.add(ResourceFactory
				.newClassPathResource("jbpm/basket/basketSubscribe.bpmn"),
				ResourceType.BPMN2);
		kbuilder.add(ResourceFactory
				.newClassPathResource("jbpm/basket/basketUnsubscribe.bpmn"),
				ResourceType.BPMN2);		
		kbuilder.add(ResourceFactory
				.newClassPathResource("jbpm/basket/basketPayment.bpmn"),
				ResourceType.BPMN2);
		kbuilder.add(ResourceFactory
				.newClassPathResource("jbpm/basket/basketBalanceInquiry.bpmn"),
				ResourceType.BPMN2);	
		kbuilder.add(ResourceFactory
				.newClassPathResource("jbpm/hr/getCV.bpmn"),
				ResourceType.BPMN2);		

		return kbuilder.newKnowledgeBase();
	}

	private StatefulKnowledgeSession createKnowledgeSession(KnowledgeBase kbase) {

		Environment env = KnowledgeBaseFactory.newEnvironment();
		env.set(EnvironmentName.ENTITY_MANAGER_FACTORY, emf);

		// { Try restoring latest session from persistence.
		try {
			ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(
					sessionManager.getLatestSessionId(), kbase, null, env);
		} catch (RuntimeException e) {
			System.out
					.println("Nie można odtworzyć ostatnio używanej sesji, zostanie utworzona nowa");
		}
		// }

		// { No session restored - create new session and save it's id.
		if (ksession == null) {
			ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase,
					null, env);

			sessionManager.saveLatestSessionId(ksession.getId());
		}
		// }

		// Register db logger.
		logger = new JPAWorkingMemoryDbLogger(ksession);

		// Register work item handlers.
		this.registerHandlers(ksession);

		// Restore asynchronous work item that were not finished.
		this.restoreWorkItems(ksession);

		// Kill process that shouldn't be restored
		this.killUnwantedProcesses(ksession);

		return ksession;
	}

	private void registerHandlers(StatefulKnowledgeSession ksession) {
		WorkItemManager workItemManager = ksession.getWorkItemManager();

		humanTaskHandler = new MinaHTWorkItemHandler(ksession);
		humanTaskHandler.setIpAddress("127.0.0.1");
		humanTaskHandler.setPort(9123);

		// connect on startup to listen for notifications from human task server
		humanTaskHandler.connect();

		GetSpreadsheetValueWorkItemHandler getSpreadsheetValueHandler = new GetSpreadsheetValueWorkItemHandler();
		getSpreadsheetValueHandler.setKSession(ksession);

		LoadPlayersListsWorkItemHandler loadPlayersHandler = new LoadPlayersListsWorkItemHandler(
				userDao);
		loadPlayersHandler.setKSession(ksession);

		HandleNewPlayerWorkItemHandler handleNewPlayerWorkItemHandler = new HandleNewPlayerWorkItemHandler();

		AddSubscriberWorkItemHandler addSubscriberWorkItemHandler = new AddSubscriberWorkItemHandler(
				userDao);

		RemoveSubscriberWorkItemHandler removeSubscriberWorkItemHandler = new RemoveSubscriberWorkItemHandler(
				userDao);

		MakeBasketPaymentWorkItemHandler makeBasketPaymentrWorkItemHandler = new MakeBasketPaymentWorkItemHandler(
				userDao);
		BalanceInquiryWorkItemHandler balanceInquiryWorkItemHandler = new BalanceInquiryWorkItemHandler(
				userDao);
		MakePaymentsWorkItemHandler makePaymentsWorkItemHandler = new MakePaymentsWorkItemHandler(userDao);
		
		BasketWeeklySummaryWorkItemHandler basketWeeklySummaryWorkItemHandler = new BasketWeeklySummaryWorkItemHandler(userDao);
		
		getCVWorkItemHandler getCVWorkItemHandler = new getCVWorkItemHandler();
		getCVWorkItemHandler.setKSession(ksession);

		workItemManager.registerWorkItemHandler("Human Task", humanTaskHandler);
		workItemManager.registerWorkItemHandler("GetSpreadsheetValue",
				getSpreadsheetValueHandler);
		workItemManager.registerWorkItemHandler("LoadPlayersList",
				loadPlayersHandler);
		workItemManager.registerWorkItemHandler("HandleNewPlayer",
				handleNewPlayerWorkItemHandler);
		workItemManager.registerWorkItemHandler("AddSubscriber",
				addSubscriberWorkItemHandler);
		workItemManager.registerWorkItemHandler("RemoveSubscriber",
				removeSubscriberWorkItemHandler);
		workItemManager.registerWorkItemHandler("MakeBasketPayment",
				makeBasketPaymentrWorkItemHandler);
		workItemManager.registerWorkItemHandler("BalanceInquiry",
				balanceInquiryWorkItemHandler);
		workItemManager.registerWorkItemHandler("MakePayments",
				makePaymentsWorkItemHandler);
		workItemManager.registerWorkItemHandler("BasketWeeklySummary",
				basketWeeklySummaryWorkItemHandler);
		workItemManager.registerWorkItemHandler("GetCV",
				getCVWorkItemHandler);
		workItemManager.registerWorkItemHandler("Email",
				emailWorkItemHandler);
	}

	/*
	 * Function sends kill signal to processes that should't be restored on
	 * startup although they are not finished
	 */
	private void killUnwantedProcesses(StatefulKnowledgeSession ksession) {

		// { Kill basket weekly jesli jest juz po czasie, w ktorym mozna sie
		// zapisywac - piątek 14:00.
		List<ProcessInstanceInfo> basketWeeklyInstances = processInstanceInfoDao
				.getRunningInstances("com.cohesiva.basket.weekly");

		if (basketWeeklyInstances != null) {

			/*
			Calendar now = Calendar.getInstance();
			int dayToday = now.get(Calendar.DAY_OF_WEEK);

			boolean toKill = false;

			if (dayToday == Calendar.FRIDAY) {

				Calendar processFinishedTime = Calendar.getInstance();
				processFinishedTime.set(Calendar.HOUR_OF_DAY, 14);
				processFinishedTime.set(Calendar.MINUTE, 0);
				processFinishedTime.set(Calendar.SECOND, 0);

				if (now.after(processFinishedTime)) {
					toKill = true;
				}
			} else {
				toKill = true;
			}
			*/
			
			boolean toKill = basketProcessServie.isTooLateToSignUp();

			if (toKill) {
				for (ProcessInstanceInfo processInstance : basketWeeklyInstances) {
					ksession.signalEvent("basketWeeklyKill", null,
							processInstance.getId());
				}
			}
		}
		// }
	}

	/*
	 * Function restores asynchronous work items of unfinished processes
	 */
	private void restoreWorkItems(StatefulKnowledgeSession ksession) {
		List<WorkItemInfo> persistedWorkItems = workItemInfoDao
				.getPersistedWorkItems();

		for (WorkItemInfo workItemInfo : persistedWorkItems) {

			String workItemName = workItemInfo.getName();

			WorkItem workItem = workItemInfo.getWorkItem(
					ksession.getEnvironment(), null);

			String workItemHandlerClassName = null;

			for (String packageName : workItemHandlersPackages) {
				try {
					workItemHandlerClassName = packageName + "." + workItemName
							+ WORK_ITEM_HANDLER_CLASS_SUFFIX;

					Class customWorkItemHandlerClass = Class
							.forName(workItemHandlerClassName);

					BaseAsynchronousWorkItemHandler customWorkItemHandler = (BaseAsynchronousWorkItemHandler) customWorkItemHandlerClass
							.newInstance();

					customWorkItemHandler.setKSession(ksession);

					customWorkItemHandler.executeWorkItem(workItem,
							ksession.getWorkItemManager());

					break;

				} catch (ClassNotFoundException e) {
					System.out.println("Class " + workItemHandlerClassName
							+ " not found");
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public MinaHTWorkItemHandler getWorkItemHandler() {
		return this.humanTaskHandler;
	}
}
