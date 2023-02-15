package site.sfire.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import site.sfire.models.Build
import site.sfire.models.OtaMeta
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.sql.Timestamp
import java.text.SimpleDateFormat


const val FILES_PATH = "/var/www/h5ai"
object Utils {

    var build: Build? = null
        private set
    var metadata: OtaMeta? = null
    var buildDate: String? = null
        private set
    var timestamp: String? = null
        private set

    private fun getLastModifiedFile(directoryFilePath: String): File? {
        val directory = File(directoryFilePath)
        val files: Array<out File>? = directory.listFiles { file ->
            file.isFile && file.extension == "zip" && file.name.contains("hentai_lavender-TwistedScarlett-ota")
        }

        var lastModifiedTime = Long.MIN_VALUE
        var chosenFile: File? = null
        if (files != null) {
            for (file in files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file
                    lastModifiedTime = file.lastModified()
                }
            }
        }

        return chosenFile
    }

    fun getChangelogDevice(): List<String> {
        val file = File("$FILES_PATH/changelog_device.txt")

        return file.readLines()
    }
    fun getChangelogSources(): List<String> {
        val file = File("$FILES_PATH/changelog_sources.txt")

        return file.readLines()
    }

    private fun hashString(input: File): String {
        val buffer = ByteArray(8192)
        var count: Int
        val digest = MessageDigest.getInstance("SHA-256")
        val bis = BufferedInputStream(FileInputStream(input))
        while (bis.read(buffer).also { count = it } > 0) {
            digest.update(buffer, 0, count)
        }
        bis.close()

        val hash = digest.digest()
        return hash.fold("") { str, it -> str + "%02x".format(it) }
    }

    private fun serveBuild() {

        val file = getLastModifiedFile(FILES_PATH)
        println("${file?.name}")

        if (file != null) {

            val isOutdated = (build?.created != file.lastModified())

            timestamp = MetaUtils.extractTimestamp(file.toPath())

            if (isOutdated && timestamp != null) {
                metadata = OtaMeta(
                    currentDownloadUrl = "https://sfire.site/${file.name}",
                    changelogUrl = "https://api.sfire.site/api/changelog",
                    originalFilename = file.name,
                    sizeBytes = file.length()
                )
                val date = Timestamp(timestamp!!.toLong() * 1000)
                buildDate = SimpleDateFormat("MMM dd, yyyy").format(date)
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            serveBuild()
        }
    }
}
