package com.verimsolution.schoolinfo.controllers

import com.verimsolution.schoolinfo.utils.USER_FOLDER
import org.springframework.http.MediaType
import org.springframework.http.MediaType.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths


@RestController
@RequestMapping()
class FileController {

    @GetMapping("blog/images/{folder}/{image}", produces = [IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE])
    fun readImage(@PathVariable image: String, @PathVariable folder: String): ByteArray {
        return Files.readAllBytes(Paths.get("$USER_FOLDER/$folder/$image"))
    }
}
