package de.brainbo.maketargets

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class MakeTargetsConfigurable : Configurable {
    private val makefileField = TextFieldWithBrowseButton()

    override fun getDisplayName(): String = "Make Targets"

    override fun createComponent(): JComponent {
        val descriptor = FileChooserDescriptor(
            true,  // chooseFiles
            false, // chooseFolders
            false, // chooseJars
            false, // chooseJarsAsFiles
            false, // chooseJarContents
            false  // chooseMultiple
        ).withTitle("Select Makefile")
         .withDescription("Choose your project's Makefile")

        makefileField.addBrowseFolderListener(
            "Select Makefile",
            "Choose your project's Makefile",
            null,
            descriptor
        )

        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Makefile path:"), makefileField, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    override fun isModified(): Boolean {
        val stored = MakeTargetsSettingsService.getInstance().state.makefilePath
        return stored != makefileField.text
    }

    override fun apply() {
        val path = makefileField.text.trim()
        if (path.isEmpty()) {
            MakeTargetsSettingsService.getInstance().state.makefilePath = ""
            return
        }
        if (!java.nio.file.Files.exists(java.nio.file.Path.of(path))) {
            Messages.showErrorDialog(makefileField, "Makefile path does not exist: $path", "Invalid Path")
            return
        }
        MakeTargetsSettingsService.getInstance().state.makefilePath = path
    }

    override fun reset() {
        makefileField.text = MakeTargetsSettingsService.getInstance().state.makefilePath
    }
}