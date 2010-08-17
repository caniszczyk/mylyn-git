/*******************************************************************************
 * Copyright (c) 2010 Chris Aniszczyk and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *     Chris Aniszczyk <caniszczyk@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.mylyn.internal.team.git.tasks;

import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskActivationListener;
import org.eclipse.ui.PlatformUI;

public class TaskActivationListener implements ITaskActivationListener {

	public void preTaskActivated(ITask task) {
		// TODO if there's a context, should we browse it to deduce which repo to choose?
		RepositoryAndBranchSelectionDialog dialog = new RepositoryAndBranchSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		dialog.open();
	}

	public void preTaskDeactivated(ITask task) {
		// TODO prompt to switch back to master
		// TODO how do we handle the case of switching to a new task from an old task
	}

	public void taskActivated(ITask task) {
		// do nothing
	}

	public void taskDeactivated(ITask task) {
		// do nothing
	}

}
