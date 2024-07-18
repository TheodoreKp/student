package com.verimsolution.schoolinfo.services

import org.springframework.web.multipart.MultipartFile

interface FileService {

    fun uploadFile(file: MultipartFile, fileName: String): String
    fun saveFile(multipartFile: MultipartFile, fileName: String, originFilePath: String): String
    fun deleteFile(fileName: String)
}