package com.verimsolution.schoolinfo.config.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class AwsProperties(
    @Value("\${aws.access-key}")
    val accessKey: String = "",
    @Value("\${aws.secret-key}")
    val secretKey: String = ""
)