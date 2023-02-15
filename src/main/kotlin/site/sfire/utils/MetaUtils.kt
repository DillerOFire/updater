package site.sfire.utils

import java.io.InputStreamReader
import java.nio.file.Path
import java.util.zip.ZipFile

const val ZIP_META_PATH = "META-INF/com/android/metadata"
object MetaUtils {

    fun extractTimestamp(zipFile: Path): String {
        val zip = ZipFile(zipFile.toFile())
        val entry = zip.getEntry(ZIP_META_PATH)
        return InputStreamReader(zip.getInputStream(entry)).readLines()[7].substringAfter("=")
    }
}