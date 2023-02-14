package site.sfire.models

import kotlinx.serialization.Serializable

@Serializable
data class Build(
    var name: String,
    var version: String,
    var buildId: String,
    var size: Int,
    var created: Long,
    var channel: String,
    var url: String,
    var sha256: String
)