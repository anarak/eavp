/*******************************************************************************
 * Copyright (c) 2014, 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Initial API and implementation and/or initial documentation -
 *   Jay Jay Billings
 *******************************************************************************/
package org.eclipse.eavp.viz.service.eclipse;

import org.eclipse.eavp.viz.service.BasicVizServiceFactory;
import org.eclipse.eavp.viz.service.IVizService;
import org.eclipse.ui.IFileEditorMapping;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.registry.EditorRegistry;
import org.eclipse.ui.internal.registry.FileEditorMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the basic implementation of the IVizServiceFactory in ICE. It
 * is registered with the platform as an OSGi service.
 *
 * The default IVizService is "ice-plot" and it is registered when the service
 * is started.
 *
 * @author Jay Jay Billings
 *
 */
public class BasicEclipseVizServiceFactory extends BasicVizServiceFactory {

	/**
	 * Logger for handling event messages and other information.
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(BasicEclipseVizServiceFactory.class);

		
	protected void associateFileType(IVizService service) {
		
		// Register the plot editor as default editor for all file
		// extensions handled by the new viz service
		for (String ext : service.getSupportedExtensions()) {

			String name = service.getName();

			// Register the plot editor as default editor for all file
			EditorRegistry editorReg = (EditorRegistry) PlatformUI
					.getWorkbench().getEditorRegistry();
			EditorDescriptor editor = (EditorDescriptor) editorReg
					.findEditor("org.eclipse.eavp.viz.service.PlotEditor");
			FileEditorMapping mapping = new FileEditorMapping(ext);
			mapping.addEditor(editor);
			mapping.setDefaultEditor(editor);

			IFileEditorMapping[] mappings = editorReg
					.getFileEditorMappings();
			FileEditorMapping[] newMappings = new FileEditorMapping[mappings.length
			                                                        + 1];
			for (int i = 0; i < mappings.length; i++) {
				newMappings[i] = (FileEditorMapping) mappings[i];
			}
			newMappings[mappings.length] = mapping;
			editorReg.setFileEditorMappings(newMappings);


			logger.info("VizServiceFactory message: " + "Viz service \"" + name
					+ "\" registered.");
		}
	}
}
