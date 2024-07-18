package com.verimsolution.schoolinfo.services.impls

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.PutObjectRequest
import com.verimsolution.schoolinfo.services.FileService
import com.verimsolution.schoolinfo.utils.USER_FOLDER
import com.verimsolution.schoolinfo.utils.USER_IMAGE_PATH
import com.verimsolution.schoolinfo.utils.getFileExtension
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption.REPLACE_EXISTING
import java.util.*


@Service
class FileServiceImpl(
    private val s3Client: AmazonS3,
    @Value("\${aws.bucket-name}")
    private val bucketName: String
) : FileService {

    private val logs = LoggerFactory.getLogger(FileServiceImpl::class.java)

    override fun uploadFile(file: MultipartFile, fileName: String): String {
        val userPath: Path = Paths.get(USER_FOLDER + fileName).toAbsolutePath().normalize()
        if (!Files.exists(userPath)) {
            Files.createDirectories(userPath)
            logs.warn("Chemin à été créer: {}", userPath)
        }
        Files.deleteIfExists(Paths.get("$userPath$fileName.jpg"))
        Files.copy(file.inputStream, userPath.resolve("$fileName.jpg"), REPLACE_EXISTING)
        logs.info("Fichier copie avec succes: {}", file.originalFilename!!)
        return getImageUrl(fileName)
    }

    /**
     * Saves a file to the AWS S3 bucket.
     *
     * @param multipartFile The file to be saved.
     * @param fileName The name of the file.
     * @param originFilePath The origin file path.
     * @return The URL of the saved file on the S3 bucket.
     */
    override fun saveFile(multipartFile: MultipartFile, fileName: String, originFilePath: String): String {
        val key =
            "$originFilePath$fileName.${getFileExtension(Objects.requireNonNull(multipartFile.originalFilename)!!)}"
        val file = convertMultipartFileToFile(multipartFile)
        val request = PutObjectRequest(bucketName, key, file)
        s3Client.putObject(request)
        file.delete()
        return "https://$bucketName.s3.us-east-2.amazonaws.com/$key"
    }

    /**
     * Deletes a file from the AWS S3 bucket.
     *
     * @param fileName The name of the file to be deleted.
     */
    override fun deleteFile(fileName: String) {
        val image = fileName.replace("https://$bucketName.s3.us-east-2.amazonaws.com/", "")
        s3Client.deleteObject(bucketName, image)
    }

    private fun getImageUrl(fileName: String): String = ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("$USER_IMAGE_PATH$fileName/$fileName.jpg").toUriString()

    /**
     * Converts a MultipartFile to a File.
     *
     * @param file The MultipartFile to be converted.
     * @return The converted File.
     */
    private fun convertMultipartFileToFile(file: MultipartFile): File {
        val convertedFile = file.originalFilename?.let { File(it) }
        try {
            if (convertedFile != null) {
                FileOutputStream(convertedFile).use { fos -> fos.write(file.bytes) }
            }
        } catch (e: IOException) {
            logs.error("Error converting multipartFile to file ", e)
        }
        return convertedFile!!
    }
}
