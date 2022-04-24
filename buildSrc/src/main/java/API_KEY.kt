import java.io.File
import java.io.FileInputStream
import java.util.*

object SecretApiKey {
    //release
    val NASA_API_KEY: String = apiKeyProperties().getProperty("NASA_API_KEY")

    //debug
    val DEMO_KEY: String = apiKeyProperties().getProperty("DEMO_KEY")

    //YouTube API Key
    val YOUTUBE_API_KEY: String = apiKeyProperties().getProperty("YOUTUBE_API_KEY")

    private fun apiKeyProperties(): Properties {
        val fileName = "app.properties"
        if (!File(fileName).exists()) {
            throw Error("You need to prepare a file called $fileName in the project root directory\n")
        }
        return Properties().apply { load(FileInputStream(fileName)) }
    }
}