package de.brainbo.maketargets

data class MakeTarget(
    val name: String,
    val description: String? = null
) {
    fun displayName(): String {
        return if (description.isNullOrBlank()) {
            name
        } else {
            "$name - $description"
        }
    }
}
