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
package com.cohesiva.processes.jbpm.serviceImpl.processes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.service.base.IJbpmBase;
import com.cohesiva.processes.jbpm.service.processes.IProcessStarterService;
import com.cohesiva.processes.jbpm.service.processes.IProcessesVariablesService;
import com.cohesiva.processes.jbpm.service.processes.basket.IBasketVariables;

@Service
public class ProcessStarterService implements IProcessStarterService {

	@Autowired
	IJbpmBase jbpmBase;

	@Autowired
	private IBasketVariables basketVariables;

	@Autowired
	private IProcessesVariablesService processVariables;

	private Map<String, String> startInfoMap = new HashMap<String, String>();

	@PostConstruct
	public void init() {
		startInfoMap
				.put("com.cohesiva.basket.subscribe",
						"Zostaniesz powiadomiony mailowo, kiedy twoje zgłoszenie subskrypcji grupy Cohesiva Basket zostanie zatwierdzone.");
		startInfoMap
				.put("com.cohesiva.basket.unsubscribe",
						"Zglosiłeś chęć zakończenia subskrypcji grupy Cohesiva Basket. Sprawdź email, aby upewnić się, że zakończyłeś subskypcję.");
		startInfoMap.put("com.cohesiva.basket.payment",
				"Aby zakupić karnet, wpłać " + basketVariables.getCarnetPrize()
						+ " PLN w sekretariacie Cohesiva.");
		startInfoMap
				.put("com.cohesiva.basket.balance.inquiry",
						"Informacje o stanie Twojego konta zostały wysłane na Twój adres email.");
	}

	public String getStartInfo(String procId) {
		return startInfoMap.get(procId);
	}

	private Map<String, Object> getStartProcessData(String processId) {
		Map<String, Object> params = new HashMap<String, Object>();

		String emailFooter = processVariables.getEmailFooter();
		params.put("emailFooter", emailFooter);

		if (processId.equals("com.cohesiva.basket.payment")) {
			params.put("carnetPrize", basketVariables.getCarnetPrize());
		} else if (processId.equals("com.cohesiva.basket.weekly")) {

			params.put("eventCarnetPrize",
					basketVariables.getEventCarnetPrize());
			params.put("eventNonCarnetPrize",
					basketVariables.getEventNonCarnetPrize());
			params.put("sufficientPlayersNumber",
					basketVariables.getBasketSufficientPlayersNumber());
			params.put("maxPlayersNumber",
					basketVariables.getBasketMaxPlayersNumber());
			params.put("basketSigningFinalHour",
					basketVariables.getBasketSigningFinalHour());
			params.put("answerLink", processVariables.getApplicationUrl());
		}

		return params;
	}

	public void startProcess(String processId, String userId) {
		StatefulKnowledgeSession session = jbpmBase.getSession();

		Map<String, Object> params = this.getStartProcessData(processId);
		params.put("userEmail", userId);

		session.startProcess(processId, params);
	}

}
