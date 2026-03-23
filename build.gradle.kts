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
        exclude("com.google.guava", "guava")
        exclude("com.google.code.gson", "gson")
        exclude("org.yaml", "snakeyaml")
        exclude("junit", "junit")
    }

    implementation(libs.litecommands)

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
