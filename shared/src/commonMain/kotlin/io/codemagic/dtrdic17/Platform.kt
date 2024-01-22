package io.codemagic.dtrdic17

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform