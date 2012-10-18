package com.cohesiva.processes.jbpm.processes.basket;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.jbpm.processes.ProcessesVariables;

@Service
public class BasketProcessService implements IBasketProcessesService {

	@Autowired
	private ProcessesVariables processesVariables;
	
	public boolean isTooLateToSignUp() {
		boolean tooLate = false;

		Calendar now = Calendar.getInstance();

		Calendar finalTime = Calendar.getInstance();
		finalTime.set(Calendar.HOUR_OF_DAY, processesVariables.getBasketFinalHour());
		finalTime.set(Calendar.MINUTE, 0);
		finalTime.set(Calendar.SECOND, 0);

		if (now.after(finalTime)) {
			tooLate = true;
		}
		
		return tooLate;
	}

}
