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

function startProcess(_id) {
	var identifier = _id;

	$.ajax({
		url : 'start_process',
		data : ({
			processId : identifier
		}),
		success : function(response) {
			$("#body").html(response);
		}
	});
}

function loadPage(mapping) {	
	$.ajax({
		url : mapping,
		success : function(response) {
			$("#body").html(response);
		}
	});
}

$('.nav-collapse .nav li').click(function(e) {
	$('.nav-collapse .nav li').removeClass('active');
	var $this = $(this);
	if (!$this.hasClass('active')) {
	$this.addClass('active');
	}
	//e.preventDefault();
	});

