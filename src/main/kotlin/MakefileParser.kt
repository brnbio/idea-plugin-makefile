package de.brainbo.maketargets

import java.io.File

object MakefileParser {

    fun listTargets(makefile: File): List<MakeTarget> {
        if (!makefile.exists()) return emptyList()

        val targetRegex = Regex("^([A-Za-z0-9_][^:#=\\t]*(?:\\s+[A-Za-z0-9_][^:#=\\t]*)*)\\s*:(?!=)\\s*(?:#\\s*(.*))?$")
        val lines = makefile.readLines()
        val targets = mutableMapOf<String, String?>()

        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.isEmpty() || trimmed.startsWith("\t")) continue

            val match = targetRegex.find(trimmed) ?: continue
            val targetNames = match.groupValues[1].trim()
            val comment = match.groupValues.getOrNull(2)?.trim()

            // Split multiple targets (e.g., "target1 target2:")
            targetNames.split(Regex("\\s+")).forEach { name ->
                val cleanName = name.trim()
                if (cleanName.isNotEmpty() &&
                    !cleanName.startsWith(".") &&
                    !cleanName.contains("%") &&
                    !cleanName.contains("=") &&
                    cleanName != "all" &&
                    cleanName != "help") {
                    targets[cleanName] = comment
                }
            }
        }

        return targets.map { (name, description) ->
            MakeTarget(name, description)
        }.sortedBy { it.name }
    }
}