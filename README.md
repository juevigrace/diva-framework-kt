# Diva Kotlin Framework

A Kotlin Multiplatform framework that provides standardized wrappers for common libraries and utilities for building cross-platform applications.

## Description

This project is a comprehensive KMP framework that simplifies the development of cross-platform applications by providing easy-to-use wrappers for popular libraries like SQLDelight for database operations and Ktor for network requests.

## Requirements

- **Kotlin**: 1.9.0 or later
- **Gradle**: 8.0 or later
- **Android Studio**: Latest stable version (for Android development)
- **Xcode**: Latest stable version (for iOS development)

## Installation

### From Source

```bash
git clone <repository-url>
cd framework-kt
./gradlew build
```

### Gradle Dependency

Add the framework to your project's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.juevigrace.diva:database:1.0.0")
    implementation("com.juevigrace.diva:network:1.0.0")
    implementation("com.juevigrace.diva:types:1.0.0")
}
```

## Building

### Using Gradle

```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :database:build
./gradlew :network:build
./gradlew :types:build

# Run tests
./gradlew test

# Publish to local repository
./gradlew publishToMavenLocal

# Clean build artifacts
./gradlew clean
```

## Usage

### Database Module

```kotlin

```

### Network Module

```kotlin

```

## Development

### Project Structure

```
framework-kt/
```

### Adding New Modules

1. Create module directory
2. Add to `settings.gradle.kts`
3. Create `build.gradle.kts`
4. Implement module functionality
5. Add tests
6. Update documentation

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass: `./gradlew test`
6. Submit a pull request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
