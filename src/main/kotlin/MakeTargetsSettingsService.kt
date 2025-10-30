package de.brainbo.maketargets

import com.intellij.openapi.components.*
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
@State(name = "MakeTargetsSettings", storages = [Storage("makeTargets.xml")])
class MakeTargetsSettingsService : PersistentStateComponent<MakeTargetsSettingsService.State> {

    data class State(
        var makefilePath: String = ""
    )

    private var state = State()

    override fun getState(): State = state

    override fun loadState(state: State) {
        this.state = state
    }

    companion object {
        fun getInstance(project: Project): MakeTargetsSettingsService = project.service()
    }
}