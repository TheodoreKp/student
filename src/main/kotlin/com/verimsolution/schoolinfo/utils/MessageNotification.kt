package com.verimsolution.schoolinfo.utils

import java.util.*

data class MessageNotification(
    val id: String,
    val text: String,
    val sender: String,
    val receiver: String,
)
