package de.brainbo.maketargets

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.ui.Messages
import java.io.File

class MakeTargetActionGroup : DefaultActionGroup(), DumbAware {

    override fun getChildren(e: AnActionEvent?): Array<AnAction> {
        val project = e?.project ?: return emptyArray()

        val settings = MakeTargetsSettingsService.getInstance(project).state
        val path = settings.makefilePath.ifBlank { return emptyArray() }

        val makefile = File(path)
        if (!makefile.exists()) return emptyArray()

        val targets = MakefileParser.listTargets(makefile)

        return targets.map { makeTarget ->
            object : AnAction(makeTarget.displayName()), DumbAware {
                override fun actionPerformed(e: AnActionEvent) {
                    TerminalRunner.runMake(project, makefile, makeTarget.name)
                }

                override fun getActionUpdateThread() = ActionUpdateThread.BGT
            }
        }.toTypedArray()
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val hasPath = if (project != null) {
            val settings = MakeTargetsSettingsService.getInstance(project).state
            settings.makefilePath.isNotBlank()
        } else {
            false
        }

        e.presentation.isEnabled = project != null && hasPath
        e.presentation.text = "Make Targets"
    }

    override fun getActionUpdateThread() = ActionUpdateThread.BGT
}
