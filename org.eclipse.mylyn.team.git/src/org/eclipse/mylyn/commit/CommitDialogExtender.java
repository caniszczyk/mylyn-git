package org.eclipse.mylyn.commit;

import org.eclipse.egit.ui.extensions.ICommitDialogExtender;
import org.eclipse.mylyn.internal.team.ui.FocusedTeamUiPlugin;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskActivityManager;
import org.eclipse.mylyn.tasks.ui.TasksUi;

@SuppressWarnings("restriction")
public class CommitDialogExtender implements ICommitDialogExtender {

	/**
	 * @author Markus Alexander Kuppe
	 * @author Manuel Doninger
	 * 
	 *         Gets the active task and combines the description and title with
	 *         the commit message template defined in the preferences
	 */
	public String getCommitMessage() {
		try {
			// get active task
			ITaskActivityManager tam = TasksUi.getTaskActivityManager();
			ITask task = tam.getActiveTask();
			if (task == null)
				return "";

			// get the commit dialog template
			String template = FocusedTeamUiPlugin.getDefault()
					.getPreferenceStore()
					.getString(FocusedTeamUiPlugin.COMMIT_TEMPLATE);
			return FocusedTeamUiPlugin.getDefault().getCommitTemplateManager()
					.generateComment(task, template);
		} catch (Exception e) {
			return "";
		}
	}

}
