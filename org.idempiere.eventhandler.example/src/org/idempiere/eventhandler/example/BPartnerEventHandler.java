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
package org.idempiere.eventhandler.example;

import org.adempiere.base.event.AbstractEventHandler;
import org.adempiere.base.event.IEventManager;
import org.adempiere.base.event.IEventTopics;
import org.compiere.model.MBPartner;
import org.compiere.model.PO;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;

@Component(immediate = true)
public class BPartnerEventHandler extends AbstractEventHandler {

	public BPartnerEventHandler() {
	}

	@Override
	protected void initialize() {
		registerTableEvent(IEventTopics.PO_AFTER_CHANGE, MBPartner.Table_Name);
	}

	@Override
	protected void doHandleEvent(Event event) {
		if (event.getTopic().equals(IEventTopics.PO_AFTER_CHANGE)) {
			 PO po = getPO(event);
			 if (po instanceof MBPartner bp) {
				 System.out.println(getClass().getName() + " | " + IEventTopics.PO_AFTER_CHANGE + " | " + bp.get_xmlString(null).toString());				 
			 }
		}
	}

	@Override
	@Reference(service = IEventManager.class, unbind = "unbindEventManager")
	public void bindEventManager(IEventManager eventManager) {
		super.bindEventManager(eventManager);
	}

	@Override
	public void unbindEventManager(IEventManager eventManager) {
		super.unbindEventManager(eventManager);
	}
}
