package de.brainbo.maketargets

import com.intellij.openapi.components.*

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
        fun getInstance(): MakeTargetsSettingsService = service()
    }
}