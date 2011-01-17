/*******************************************************************************
 * Copyright (c) 2011 Chris Aniszczyk and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *     Chris Aniszczyk <caniszczyk@gmail.com> - initial API and implementation
 *******************************************************************************/

package org.eclipse.egit.internal.mylyn.ui.changesets;

import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.team.ui.AbstractActiveChangeSetProvider;
import org.eclipse.mylyn.team.ui.IContextChangeSet;
import org.eclipse.team.internal.core.subscribers.ActiveChangeSetManager;

public class GitActiveChangeSetProvider extends AbstractActiveChangeSetProvider {
	
	@Override
	public ActiveChangeSetManager getActiveChangeSetManager() {
		// TODO
		return null;
	}

	@Override
	public IContextChangeSet createChangeSet(ITask task) {
		return null; // TODO
	}

}
