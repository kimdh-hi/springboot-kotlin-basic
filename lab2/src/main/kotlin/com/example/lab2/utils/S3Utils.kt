package com.example.lab2.utils

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.util.IOUtils
import com.example.lab2.dto.response.S3UploadResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID


@Component
class S3Utils() {

    @Value("\${cloud.aws.s3.bucket}") lateinit var bucket: String
    @Autowired lateinit var amazonS3Client: AmazonS3

    @Throws(IOException::class)
    fun upload(multipartFile: MultipartFile, dirName: String?): S3UploadResponseDto {
        val uploadFile: File? = convert(multipartFile)
        val s3UploadUrl = upload(uploadFile, dirName)

        return S3UploadResponseDto(multipartFile.originalFilename as String, s3UploadUrl as String)
    }

    @Throws(IOException::class)
    fun download(fileName: String): ByteArray? {
        val s3Object: S3Object? = amazonS3Client.getObject(bucket, fileName)
        val s3ObjectContent: S3ObjectInputStream? = s3Object?.objectContent
        val bytes = s3ObjectContent?.run {
            IOUtils.toByteArray(s3ObjectContent)
        }

        return bytes
    }

    // S3로 파일 업로드하기
    private fun upload(uploadFile: File?, dirName: String?): String? {
        val fileName = dirName + "/" + UUID.randomUUID() + uploadFile?.getName() // S3에 저장된 파일 이름
        val uploadImageUrl = putS3(uploadFile, fileName) // s3로 업로드

        println("fileName = " + fileName)
        println("uploadImageUrl = " + uploadImageUrl)

        removeNewFile(uploadFile)
        return uploadImageUrl
    }

    // S3로 업로드
    private fun putS3(uploadFile: File?, fileName: String): String {
        amazonS3Client.putObject(bucket, fileName, uploadFile)
        return amazonS3Client.getUrl(bucket, fileName).toString()
    }

    // 로컬에 저장된 이미지 지우기
    private fun removeNewFile(targetFile: File?) {
        targetFile?.delete()
    }

    // 로컬에 파일 업로드 하기
    @Throws(IOException::class)
    private fun convert(file: MultipartFile): File? {
        val convertFile = File(System.getProperty("user.dir") + "/" + file.originalFilename)
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            FileOutputStream(convertFile).use { fos ->  // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.bytes)
            }
        }
        return convertFile
    }
}