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
package com.cohesiva.processes.jbpm.processes.basket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.processes.ProcessivaProcess;
import com.cohesiva.processes.jbpm.service.processes.IProcessesVariablesService;
import com.cohesiva.processes.jbpm.service.processes.basket.IBasketVariables;

@Service
public class BasketWeeklyProcess extends ProcessivaProcess {
	
	@Autowired
	private IBasketVariables basketVariables;
	
	@Autowired
	private IProcessesVariablesService processVariables;

	@Override
	protected void initProcessId() {
		setProcessId("com.cohesiva.basket.weekly");
	}

	@Override
	protected void initInitData() {
		Map<String,Object> initData = new HashMap<String, Object>();
		
		initData.put("eventCarnetPrize",
				basketVariables.getEventCarnetPrize());
		initData.put("eventNonCarnetPrize",
				basketVariables.getEventNonCarnetPrize());
		initData.put("sufficientPlayersNumber",
				basketVariables.getBasketSufficientPlayersNumber());
		initData.put("maxPlayersNumber",
				basketVariables.getBasketMaxPlayersNumber());
		initData.put("basketSigningFinalHour",
				basketVariables.getBasketSigningFinalHour());
		initData.put("answerLink", processVariables.getApplicationUrl());
		
		this.setInitData(initData);
	}

	@Override
	public boolean isAllowedToViewNow(String userId) {
		return false;
	}
}
