package com.example.lab2.utils

import com.amazonaws.AmazonClientException
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.transfer.TransferManager
import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import com.amazonaws.services.s3.transfer.Upload
import com.example.lab2.dto.response.S3UploadResponseDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*


@Component
class S3Uploader(
    private val amazonS3Client: AmazonS3Client
) {

    @Value("\${cloud.aws.credentials.accessKey}") lateinit var accessKey: String
    @Value("\${cloud.aws.credentials.secretKey}") lateinit var secretKey: String
    @Value("\${cloud.aws.region.static}") lateinit var region: String
    @Value("\${cloud.aws.s3.bucket.name}") lateinit var bucket: String
    @Value("\${cloud.aws.s3.bucket.url}") lateinit var bucketUrl: String

    fun upload(multipartFile: MultipartFile): S3UploadResponseDto {
        val inputStream = multipartFile.inputStream
        val metaData = getMetaData(multipartFile)
        val saveFileName = generateSaveFileName(multipartFile.originalFilename as String)

        val uploadUrl = upload(inputStream, metaData, saveFileName)

        return S3UploadResponseDto(multipartFile.originalFilename as String, uploadUrl)
    }

    private fun upload(inputStream: InputStream, metaData: ObjectMetadata, saveFileName: String): String  {
        val transferManager: TransferManager = TransferManagerBuilder.standard().withS3Client(amazonS3Client).build()

        val putRequest = PutObjectRequest(
            bucket,
            saveFileName,
            inputStream,
            metaData
        ).withCannedAcl(CannedAccessControlList.PublicRead)

        val upload: Upload = transferManager.upload(putRequest)

        try {
            upload.waitForCompletion()
        } catch (amazonClientException: AmazonClientException) {
            throw AmazonClientException("S3 업로드 실패")
        } catch (e: InterruptedException) {
            throw InterruptedException("S3 업로드 실패")
        }

        return bucketUrl + saveFileName
    }

    private fun deleteFile(file: File?) {
        file?.delete()
    }

    private fun getMetaData(file: MultipartFile): ObjectMetadata {
        val objectMetadata: ObjectMetadata = ObjectMetadata()
        objectMetadata.contentType = file.contentType
        objectMetadata.contentLength = file.bytes.size.toLong()

        return objectMetadata
    }

    private fun generateSaveFileName(originalFilename: String?): String {
        val extPosIndex: Int? = originalFilename?.lastIndexOf(".")
        val ext = originalFilename?.substring(extPosIndex?.plus(1) as Int)

        return UUID.randomUUID().toString() + "." + ext
    }
}