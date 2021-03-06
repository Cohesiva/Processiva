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

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.jbpm.processes.ProcessivaProcess;
import com.cohesiva.processes.jbpm.service.processes.IProcessStarterService;

@Service
public class BasketSubscriptionProcess extends ProcessivaProcess {

	@Autowired
	private UserDao userDao;

	@Autowired
	private IProcessStarterService processStarterService;

	@Override
	protected void initProcessId() {
		setProcessId("com.cohesiva.basket.subscribe");
	}

	@Override
	protected void initStartProcessInfo() {
		super.initStartProcessInfo();

		this.setStartProcessInfo("Zostaniesz powiadomiony mailowo, kiedy twoje zgłoszenie subskrypcji grupy Cohesiva Basket zostanie zatwierdzone.");
	}

	@Override
	protected void initAuthorizedGroups() {
		super.initAuthorizedGroups();
		
		this.authorizedGroups = new ArrayList<String>(Arrays.asList("ALL"));
	}

	@Override
	public boolean isAllowedToViewNow(String userId) {
		boolean result = true;

		if (userDao.isSubscribingBasket(userId)) {
			return false;
		}

		if (processStarterService.isProcessStartedByUser(processId, userId)) {
			return false;
		}

		return result;
	}
}
