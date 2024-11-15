/***********************************************************************
 * This file is part of iDempiere ERP Open Source                      *
 * http://www.idempiere.org                                            *
 *                                                                     *
 * Copyright (C) Contributors                                          *
 *                                                                     *
 * This program is free software; you can redistribute it and/or       *
 * modify it under the terms of the GNU General Public License         *
 * as published by the Free Software Foundation; either version 2      *
 * of the License, or (at your option) any later version.              *
 *                                                                     *
 * This program is distributed in the hope that it will be useful,     *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of      *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
 * GNU General Public License for more details.                        *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with this program; if not, write to the Free Software         *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
 * MA 02110-1301, USA.                                                 *
 **********************************************************************/
package org.idempiere.rest.resource.ext.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.compiere.model.MTax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trekglobal.idempiere.rest.api.v1.resource.impl.ModelResourceImpl;

@Path("v1/tax")
public class TaxResource {

	@Context
	private ResourceContext resourceContext;
	
	public TaxResource() {
	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * Get record by id/uuid
	 * @param id id/uuid
	 * @return json representation of record
	 */
	public Response getTax(@PathParam("id") String id) {
		ModelResourceImpl modelResource = resourceContext.getResource(ModelResourceImpl.class);
		Response response = modelResource.getPO(MTax.Table_Name, id, null, null, null);
		String json = (String) response.getEntity();
		ObjectMapper input = new ObjectMapper();
		ObjectMapper output = new ObjectMapper();
		ObjectNode outNode = output.createObjectNode();
		try {
			JsonNode node = input.readTree(json);			
			outNode.put("id", node.get("id").asInt());
			outNode.put("name", node.get("Name").asText());
			outNode.put("rate", node.get("Rate").asDouble());
			outNode.put("category", node.get("C_TaxCategory_ID").get("identifier").asText());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		try {
			return Response.ok(output.writerWithDefaultPrettyPrinter().writeValueAsString(outNode)).build();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
}
