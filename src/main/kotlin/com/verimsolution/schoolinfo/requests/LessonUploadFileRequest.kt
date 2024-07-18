package com.verimsolution.schoolinfo.requests

import org.springframework.web.multipart.MultipartFile

data class LessonUploadFileRequest(
    val id: String,
    val file: MultipartFile?
)
