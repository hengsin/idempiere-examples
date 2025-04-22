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
package org.idempiere.process.annotation.mapped.example;

import org.adempiere.plugin.utils.Incremental2PackActivator;
import org.idempiere.process.IMappedProcessFactory;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

@Component(immediate = true)
public class MyActivator extends Incremental2PackActivator {

	public MyActivator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		//Use @Reference instead of Core.getMappedProcessFactory() to get the IMappedProcessFactory instance
		//@Activate will only be called after @Reference is injected
		//This is safer than using Core.getMappedProcessFactory() as it is not guaranteed that the IMappedProcessFactory 
		//is available at the start time of this bundle	
		//Core.getMappedProcessFactory().scan(context, "org.idempiere.process.annotation.mapped.example");
	}

	@Reference(service = IMappedProcessFactory.class, cardinality = ReferenceCardinality.MANDATORY) 
	private IMappedProcessFactory mappedProcessFactory;
	
	@Activate
	public void activate(BundleContext context) {
		mappedProcessFactory.scan(context, "org.idempiere.process.annotation.mapped.example");
	}
}
