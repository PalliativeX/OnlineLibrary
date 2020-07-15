package com.base.onlinelib

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OnlineLibApp

fun main(args: Array<String>) {
	runApplication<OnlineLibApp>(*args)
}
