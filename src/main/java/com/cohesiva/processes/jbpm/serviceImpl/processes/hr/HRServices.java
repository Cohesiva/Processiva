package com.cohesiva.processes.jbpm.serviceImpl.processes.hr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cohesiva.processes.db.UserDao;
import com.cohesiva.processes.jbpm.service.processes.hr.IHRServices;

@Service
public class HRServices implements IHRServices {
	
	@Autowired
	UserDao userDao;

	public List<String> getEmployeesList() {
		List<String> employeesList = userDao.getUsersEmails();
		
		return employeesList;
	}

	public List<String> getCVFormats() {
		List<String> cvFormats = new ArrayList<String>();
		
		cvFormats.add("Pdf");
		cvFormats.add("Odt");
		
		return cvFormats;
	}

}
