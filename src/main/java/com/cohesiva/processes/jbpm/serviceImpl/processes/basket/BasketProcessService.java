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

	public boolean isTooLateToSignUp() {
		boolean result = true;

		Calendar now = Calendar.getInstance();

		// { if process is being restored on different day the process should
		// take place, don't restore it
		int dayToday = now.get(Calendar.DAY_OF_WEEK);

		if (dayToday != basketVariables.getBasketWeeklyDay()) {
			return false;
		}
		// }

		// { if the process is being restored after the final signing hour of
		// basket, don't restore it
		Calendar finalTime = Calendar.getInstance();
		finalTime.set(Calendar.HOUR_OF_DAY,
				new Integer(basketVariables.getBasketSigningFinalHour()));
		finalTime.set(Calendar.MINUTE, 0);
		finalTime.set(Calendar.SECOND, 0);

		if (now.after(finalTime)) {
			return false;
		}
		// }

		return result;
	}

}
