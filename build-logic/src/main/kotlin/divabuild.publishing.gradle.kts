import divabuild.internal.libs

plugins {
    id("com.vanniktech.maven.publish")
    id("signing")
}

mavenPublishing {
    coordinates("io.github.juevigrace", project.name, libs.versions.diva.version.get())

    pom {
        name.set(project.name)
        description.set(
            project.description.orEmpty()
                .ifEmpty { "Diva framework for making KMP applications development more simple" },
        )
        url.set("https://github.com/juevigrace/diva-framework-kt/")

        licenses {
            license {
                name.set("The MIT License")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        developers {
            developer {
                id.set("juevigrace")
                name.set("Daniel Zabala")
                url.set("https://github.com/juevigrace/")
            }
        }

        scm {
            url.set("https://github.com/juevigrace/diva-framework-kt")
            connection.set("scm:git:git://github.com/juevigrace/diva-framework-kt.git")
            developerConnection.set("scm:git:ssh://github.com:juevigrace/diva-framework-kt.git")
        }
    }
}

signing {
    sign(publishing.publications)
}
