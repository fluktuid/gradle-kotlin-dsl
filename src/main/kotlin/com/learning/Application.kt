package com.learning

import java.io.File

object Application {
    @JvmStatic
    fun main(args: Array<String>) {
        println("APP-NAME    -----> ${System.getenv("APP_NAME") ?: "APP_NAME is missing"}")
        println("PLUGIN-USED -----> ${System.getenv("PLUGIN_USED") ?: "PLUGIN_USED is missing"}")
        println("Application is running")
        println("hello world")

        File("FooBar.txt").useLines { fooBarLines ->
            fooBarLines.forEach { println(it) }
        }

        println("bye bye world")
        println("Application shutting down")
    }
}
