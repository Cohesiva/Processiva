
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

package com.cohesiva.processes.jbpm.service.base;

import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.MinaHTWorkItemHandler;

/*
 * Service for controlling jbpm engine
 */
public interface IJbpmBase {

	public StatefulKnowledgeSession getSession();	
	
	public MinaHTWorkItemHandler getHumanTaskHandler();
	
	public void signalEvent(String event, String processId);
}
