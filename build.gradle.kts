plugins {
    java

    alias(libs.plugins.shadow)
}

group = "me.fortibrine"
version = "1.0"

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.panda-lang.org/releases")
}

dependencies {
    compileOnly(libs.paper) {
        exclude("commons-lang", "commons-lang")
        exclude("org.yaml", "snakeyaml")
        exclude("junit", "junit")
    }

    compileOnly(libs.sqlite)

    implementation(libs.litecommands) {
        exclude("org.jetbrains", "annotations")
    }
    implementation(libs.configurate)
    implementation(libs.ormlite)
    implementation(fileTree("libs") {
        include("*.jar")
    })

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs.add("-parameters")
    }

    shadowJar {
        archiveClassifier.set("")

        relocate("org.spongepowered.configurate", "${rootProject.group}.${rootProject.name.lowercase()}.shade.configurate")
        relocate("dev.rollczi.litecommands", "${rootProject.group}.${rootProject.name.lowercase()}.shade.litecommands")
        relocate("com.j256.ormlite", "${rootProject.group}.${rootProject.name.lowercase()}.shade.ormlite")
        relocate("io.leangen.geantyref", "${rootProject.group}.${rootProject.name.lowercase()}.shade.geantyref")
        relocate("net.kyori", "${rootProject.group}.${rootProject.name.lowercase()}.shade.kyori")
        relocate("ru.boomearo.menuinv", "${rootProject.group}.${rootProject.name.lowercase()}.shade.menuinv")
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
        filesMatching("config.yml") {
            expand("version" to project.version)
        }
    }
}
