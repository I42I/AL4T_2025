import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.api.JavaVersion
import org.gradle.api.tasks.compile.JavaCompile

subprojects {
    plugins.withType<JavaPlugin> {
        // Configure the Java plugin extension once the Java plugin is applied
        project.extensions.configure<JavaPluginExtension> {
            // Enforce Java 21 across all Java subprojects via toolchains
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(21))
            }
            // Also set source/target compatibility explicitly
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
        // Ensure the compiler uses the correct --release flag
        tasks.withType<JavaCompile>().configureEach {
            options.release.set(21)
        }
    }
}