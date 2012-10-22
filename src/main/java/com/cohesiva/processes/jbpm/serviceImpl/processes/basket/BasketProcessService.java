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
package com.cohesiva.processes.jbpm.serviceImpl.processes.basket;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.service.processes.basket.IBasketProcessesService;
import com.cohesiva.processes.jbpm.service.processes.basket.IBasketVariables;

@Service
public class BasketProcessService implements IBasketProcessesService {

	@Autowired
	private IBasketVariables basketVariables;
	
	public boolean isTooLateToSignUp() {/*
		boolean tooLate = false;

		Calendar now = Calendar.getInstance();

		Calendar finalTime = Calendar.getInstance();
		finalTime.set(Calendar.HOUR_OF_DAY, new Integer(basketVariables.getBasketSigningFinalHour()));
		finalTime.set(Calendar.MINUTE, 0);
		finalTime.set(Calendar.SECOND, 0);

		if (now.after(finalTime)) {
			tooLate = true;
		}
		
		return tooLate;*/
		return false;
	}

}
