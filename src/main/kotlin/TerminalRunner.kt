package de.brainbo.maketargets

import com.intellij.openapi.project.Project
import com.intellij.terminal.ui.TerminalWidget
import org.jetbrains.plugins.terminal.TerminalToolWindowManager
import java.io.File

object TerminalRunner {

    fun runMake(project: Project, makefile: File, target: String) {

        val wd = makefile.parentFile?.absolutePath ?: project.basePath

        val terminalManager = TerminalToolWindowManager.getInstance(project)
        val widget: TerminalWidget = terminalManager.createShellWidget(wd, "Make: $target", /*requestFocus=*/ true, /* deferSessionStartUntilUiShown = */ false)
        widget.sendCommandToExecute("make $target")
    }

}