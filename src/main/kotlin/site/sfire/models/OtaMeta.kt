package site.sfire.models

import kotlinx.serialization.Serializable

@Serializable
data class OtaMeta(
    val currentDownloadUrl: String,
    val changelogUrl: String,
    val originalFilename: String,
    val type: OtaType = OtaType.UNKNOWN,
    val sizeBytes: Long,
    val wipe: Boolean = false,
    val downgrade: Boolean = false
) {
    enum class OtaType(value: Int) {
        UNKNOWN(0),
        AB(1),
        BLOCK(2),
        BRICK(3)
    }
}