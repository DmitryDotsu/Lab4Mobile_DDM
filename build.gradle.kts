@file:Suppress("DEPRECATION")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.6.0" apply false  // Обновлено с 8.2.0 до 8.5.0
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false  // Временно откатываем до стабильной 2.0.21
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}