package com.verimsolution.schoolinfo.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageRequest(
    val text: String,
    val sender: String,
    val receiver: String,
) {

    constructor() : this(
        text = "",
        sender = "",
        receiver = ""
    )
}
