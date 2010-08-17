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

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.RepositoryUtil;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewContentProvider;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewLabelProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RepositoryAndBranchSelectionDialog extends TitleAreaDialog {

	private TableViewer repositoryTableViewer;
	private Combo branchCombo;
	private RepositoryUtil util = Activator.getDefault().getRepositoryUtil();

	public RepositoryAndBranchSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Composite main = new Composite(composite, SWT.NONE);

		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(main);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
		
		Label repositoryLabel = new Label(main, SWT.NONE);
		repositoryLabel.setText("Select a repository:");
		GridDataFactory.fillDefaults().span(3, 1).grab(true, false).applyTo(
				repositoryLabel);

		repositoryTableViewer = new TableViewer(main, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);
		repositoryTableViewer.setContentProvider(new RepositoriesViewContentProvider());
		GridDataFactory.fillDefaults().span(3, 1).grab(true, true).applyTo(repositoryTableViewer.getTable());
		repositoryTableViewer.setLabelProvider(new RepositoriesViewLabelProvider());
		repositoryTableViewer.setInput(util.getConfiguredRepositories());

		// TODO set the initial repository

		Label branchLabel = new Label(main, SWT.NONE);
		branchLabel.setText("Branch: ");

		branchCombo = new Combo(main, SWT.READ_ONLY | SWT.DROP_DOWN);

		// TODO fill branch based on repo selection

		GridDataFactory.fillDefaults().span(2, 1).grab(true, false).applyTo(
				branchCombo);

		return main;
	}

}
