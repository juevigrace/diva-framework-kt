plugins {
    id("divabuild.setup-common")
    id("divabuild.targets-jvm")
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Database
            implementation(projects.database)

            // Util
            implementation(projects.util)
        }
    }
}

sqldelight {
    databases {
        create("DivaDB") {
            packageName.set("io.github.juevigrace.diva.examples.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            generateAsync.set(true)
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }
    }
}
