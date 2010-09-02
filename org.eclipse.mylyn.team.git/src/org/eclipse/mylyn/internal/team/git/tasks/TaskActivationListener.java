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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskActivationListener;
import org.eclipse.ui.PlatformUI;

public class TaskActivationListener implements ITaskActivationListener {

	public void preTaskActivated(ITask task) {
		// TODO if there's a context, should we browse it to deduce which repo to choose?
		String branch = Constants.R_HEADS + task.getTaskId();
		RepositoryAndBranchSelectionDialog dialog = new RepositoryAndBranchSelectionDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), branch);
		if (dialog.open() == Window.OK) {
			try {
				BranchOperation operation = new BranchOperation(dialog.getRepository(), dialog.getBranch());
				operation.execute(null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void preTaskDeactivated(ITask task) {
		// TODO how do we handle the case of switching to a new task from an old task
	}

	public void taskActivated(ITask task) {
		// do nothing
	}

	public void taskDeactivated(ITask task) {
		// FIXME hack, we should detect which repository to switch to master
		// we should base this off the task context imho... we should be able to guess based on the projects in the context
		// if we get a conflict... this may be a bit more complicated... but how common would this be?
		Repository repository = Activator.getDefault().getRepositoryCache().getAllRepositories()[0];
		try {
			BranchOperation operation = new BranchOperation(repository, Constants.MASTER);
			operation.execute(null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

}
