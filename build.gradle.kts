plugins {
    java

    alias(libs.plugins.shadow)
}

group = "me.fortibrine"
version = "1.0"

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)

    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()

        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
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
