package de.brainbo.maketargets

import com.intellij.openapi.project.Project
import org.jetbrains.plugins.terminal.TerminalView
import java.io.File

object TerminalRunner {

    fun runMake(project: Project, makefile: File, target: String) {

        val wd = makefile.parentFile?.absolutePath ?: project.basePath

        val term = TerminalView.getInstance(project)
            .createLocalShellWidget(wd, "Make: $target")

        term.executeCommand("make $target")
    }

}