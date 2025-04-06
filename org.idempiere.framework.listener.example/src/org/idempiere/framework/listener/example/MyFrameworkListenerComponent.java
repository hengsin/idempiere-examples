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
package org.idempiere.framework.listener.example;

import org.osgi.framework.FrameworkListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkEvent;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

@Component(immediate = true) // immediate=true ensures it starts when the bundle does
public class MyFrameworkListenerComponent implements FrameworkListener {

    private BundleContext bundleContext;

    @Activate
    protected void activate(BundleContext context) {
        this.bundleContext = context;
        context.addFrameworkListener(this); // 'this' is the listener instance
    }

    @Deactivate
    protected void deactivate(BundleContext context) {
        // Use the stored context or the one passed to deactivate
        // Using the one passed is often safer if the component manages context itself
        if (context != null) {
             context.removeFrameworkListener(this);
        } else if (this.bundleContext != null) {
            // Fallback, though context should ideally be passed to deactivate
            this.bundleContext.removeFrameworkListener(this);
        }
        this.bundleContext = null;
    }

    @Override
    public void frameworkEvent(FrameworkEvent event) {
        String eventType;
        switch (event.getType()) {
            // ... (cases for STARTED, ERROR, etc.) ...
            case FrameworkEvent.ERROR:
                eventType = "ERROR";
                System.err.println("DS Listener - OSGi Framework Error: " + event.getThrowable().getMessage());
                break;
            case FrameworkEvent.STARTLEVEL_CHANGED:
				eventType = "STARTLEVEL_CHANGED";
				started();
				break;
            default:
                eventType = "OTHER (" + event.getType() + ")";
                break;
        }
        System.out.println("DS Listener - Received OSGi Framework Event: " + eventType +
                           (event.getBundle() != null ? " from Bundle: " + event.getBundle().getSymbolicName() : ""));
    }

	private void started() {
		Bundle[] bundles = bundleContext.getBundles();
		List<String[]> bundleLists = new ArrayList<>();
		for (Bundle bundle : bundles) {
			bundleLists.add(new String[]{bundle.getSymbolicName(), getBundleState(bundle)});
		}
		Collections.sort(bundleLists, (a, b) -> a[0].compareTo(b[0]));
	    bundleLists.forEach(e -> System.out.println("Bundle: " + e[0] + " State: " + e[1]));
	}

	private String getBundleState(Bundle bundle) {
		switch (bundle.getState()) {
			case Bundle.ACTIVE:
				return "ACTIVE";
			case Bundle.INSTALLED:
				return "INSTALLED";
			case Bundle.RESOLVED:
				return "RESOLVED";
			case Bundle.STARTING:
				return "STARTING";
			case Bundle.STOPPING:
				return "STOPPING";
			case Bundle.UNINSTALLED:
				return "UNINSTALLED";
			default:
				return "UNKNOWN";
						
		}
	}
}
