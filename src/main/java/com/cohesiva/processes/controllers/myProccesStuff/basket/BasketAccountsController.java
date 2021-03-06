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
package com.cohesiva.processes.controllers.myProccesStuff.basket;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cohesiva.processes.controllers.myProccesStuff.basket.pojo.BasketPlayer;
import com.cohesiva.processes.controllers.myProccesStuff.basket.pojo.SubData;
import com.cohesiva.processes.db.UserDao;

@Controller
@RequestMapping(value = "/basket")
public class BasketAccountsController {

	@Autowired
	UserDao userDao;

	/*
	 * @RequestMapping(method = RequestMethod.GET)
	 * 
	 * @ResponseBody public final List<Foo> getAll() { return service.getAll();
	 * }
	 * 
	 * @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	 * 
	 * @ResponseBody public final Foo get(@PathVariable("id") final Long id) {
	 * return RestPreconditions.checkNotNull(service.getById(id)); }
	 */

	/*
	 * @RequestMapping(value = "/sub", method = RequestMethod.GET)
	 * 
	 * @ResponseStatus(HttpStatus.OK)
	 * 
	 * @ResponseBody public final String test() { System.out.println("wejszlo");
	 * 
	 * return "odp"; }
	 */

	@RequestMapping(value = "/balance/{email}", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public BasketPlayer test(@RequestBody SubData subData) {
		String email = subData.getEmail();
		
		int balance = userDao.getBalance(email);

		BasketPlayer player = new BasketPlayer();
		player.setBalance(balance);

		Date expirationDate = userDao.getBalanceExpirationDate(email);

		if (expirationDate != null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String expDate = dateFormat.format(expirationDate);

			player.setExpirationDate(expDate);
		}

		return player;
	}

	@RequestMapping(value = "/sub", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public final String create(@RequestBody SubData subData) {
		if (subData.isAddSub()) {
			userDao.setBasketSubscription(subData.getEmail());
		} else {
			userDao.unsetBasketSubscription(subData.getEmail());
		}

		return "post";
	}

	@RequestMapping(value = "/sub", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public final String update(@RequestBody SubData subData) {
		userDao.setBasketSubscription(subData.getEmail());

		System.out.println(subData.getEmail());

		return "put";
	}

	/*
	 * @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	 * 
	 * @ResponseStatus(HttpStatus.OK) public final void
	 * delete(@PathVariable("id") final Long id) { service.deleteById(id); }
	 */
}
