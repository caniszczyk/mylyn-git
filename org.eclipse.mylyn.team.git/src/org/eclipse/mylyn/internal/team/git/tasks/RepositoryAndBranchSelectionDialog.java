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

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIIcons;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewContentProvider;
import org.eclipse.egit.ui.internal.repository.RepositoriesViewLabelProvider;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
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
	private String initialBranch;
	private String branch;
	private List<Repository> repos = new LinkedList<Repository>();
	private Map<String, String> branchesForCombo = new HashMap<String, String>();

	public RepositoryAndBranchSelectionDialog(Shell parentShell, String initialBranch) {
		super(parentShell);
		this.initialBranch = initialBranch;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setLayout(new GridLayout(3, false));

		Label repositoryLabel = new Label(composite, SWT.NONE);
		repositoryLabel.setText("Select a repository:");
		GridDataFactory.fillDefaults().span(3, 1).grab(true, false).applyTo(
				repositoryLabel);

		repositoryTableViewer = new TableViewer(composite, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER | SWT.MULTI);
		repositoryTableViewer.setContentProvider(new RepositoriesViewContentProvider());
		GridDataFactory.fillDefaults().span(3, 1).grab(true, true).applyTo(repositoryTableViewer.getTable());
		repositoryTableViewer.setLabelProvider(new RepositoriesViewLabelProvider());
		repositoryTableViewer.setInput(util.getConfiguredRepositories());

		// TODO use a ComboViewer
		branchCombo = new Combo(composite, SWT.DROP_DOWN);
		branchCombo.setLayoutData(GridDataFactory.fillDefaults().span(3,1).grab(true, false).create());
		branchCombo.setEnabled(false);
		
		repositoryTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				try {
					branchCombo.setEnabled(true);
					branchCombo.removeAll();
					branchCombo.setText(initialBranch);
					repos = getRepositories();
					
					for (Repository repository : repos) {
						if (repository != null) {
							for (Entry<String, Ref> refEntry : repository.getRefDatabase()
									.getRefs(Constants.R_HEADS).entrySet()) {
								if (!refEntry.getValue().isSymbolic())
																		
									branchesForCombo.put(refEntry.getValue().getName(), refEntry.getValue().getName());	
	
							}
						}
					}
					
					for (String b : branchesForCombo.keySet()) {
						branchCombo.add(b);
					}
				} catch (IOException e) {
					// do nothing atm
				}
			}

		});
		
		// TODO how do we handle multiple repos?
		// need to figure out things..
		branchCombo.setText(initialBranch);
		branch = initialBranch;
		
		branchCombo.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				branch = branchCombo.getText();					
			}

			public void focusGained(FocusEvent e) {
				// Nothing to do				
			}
		});
		
		setTitle("Select Branch");
		setMessage("Select a repository and corresponding branch to checkout.");
		setTitleImage(UIIcons.WIZBAN_CONNECT_REPO.createImage());
		applyDialogFont(composite);
		return composite;
	}
	
	public String getBranch() {
		return branch;
	}
	
	private List<Repository> getRepositories() {
		List<Repository> reposList = new LinkedList<Repository>();
		Iterator<RepositoryTreeNode> it = ((IStructuredSelection) repositoryTableViewer.getSelection()).iterator();
		
		while (it.hasNext()) {
			reposList.add(it.next().getRepository());
		}
		
		return reposList;
	}

	public List<Repository> getSelectedRepositories() {
		if (repos.isEmpty()) {
			return null;
		}
		return repos;
	}
}
