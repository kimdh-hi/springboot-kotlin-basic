package com.dhk.ecommerce.common.utils

import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@Component
class S3Utils(private val s3Client: AmazonS3Client) {

    @Value("\${cloud.aws.s3.bucket}") lateinit var bucket: String

    @Throws(IOException::class)
    fun upload(multipartFile: MultipartFile, dir: String): String {
        val convertedFile = convert(multipartFile)
        val extensionName = convertedFile.extension
        val savePath = UUID.randomUUID().toString() + extensionName

        s3Client.putObject(bucket, savePath, convertedFile)
        removeNewFile(convertedFile)

        return s3Client.getUrl(bucket, savePath).toString()
    }

    // 로컬에 파일 업로드 하기
    @Throws(IOException::class)
    private fun convert(file: MultipartFile): File {
        val convertFile = File(file.originalFilename) // 프로젝트 root 경로에 파일이 생성된다.
        if (convertFile.createNewFile()) {
            FileOutputStream(convertFile).use { fos ->
                fos.write(file.bytes)
            }
        }
        return convertFile
    }

    // 로컬에 저장된 이미지 지우기
    private fun removeNewFile(targetFile: File?) {
        targetFile?.delete()
    }
}