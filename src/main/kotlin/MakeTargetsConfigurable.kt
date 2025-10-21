package de.brainbo.maketargets

import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.options.BoundConfigurable
import com.intellij.openapi.ui.DialogPanel
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import java.nio.file.Files
import java.nio.file.Path

class MakeTargetsConfigurable : BoundConfigurable("Make Targets") {

    private val settingsService = MakeTargetsSettingsService.getInstance()

    override fun createPanel(): DialogPanel {
        val descriptor: FileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor()
            .withTitle("Select Makefile")
            .withDescription("Choose your project's Makefile")

        return panel {
            row("Makefile path:") {
                textFieldWithBrowseButton(
                    browseDialogTitle = "Select Makefile",
                    fileChooserDescriptor = descriptor
                )
                    .bindText(
                        getter = { settingsService.state.makefilePath },
                        setter = { settingsService.state.makefilePath = it.trim() }
                    )
                    .comment("Choose your project's Makefile")
                    .validationOnApply { component ->
                        val path = component.text.trim()
                        if (path.isEmpty()) {
                            null
                        } else if (!Files.exists(Path.of(path))) {
                            ValidationInfo("Makefile path does not exist: $path", component)
                        } else {
                            null
                        }
                    }
            }
        }
    }
}
