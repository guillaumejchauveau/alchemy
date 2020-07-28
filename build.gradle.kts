plugins {
  base
  java
}

allprojects {
  group = "ovh.gecu.alchemy"
  version = "2.0"
  repositories {
    mavenCentral()
  }
}

tasks.clean {
  subprojects.forEach {
    val cleanTask = it.tasks.findByName("clean")
    if (cleanTask != null) {
      dependsOn(cleanTask)
    }
  }
}

subprojects {
  apply(plugin = "jacoco")
  apply(plugin = "java")
  apply(plugin = "maven-publish")
  apply(plugin = "java-library")
  apply(plugin = "java-library-distribution")

  java {
    sourceCompatibility = JavaVersion.VERSION_11
  }

  tasks.withType<JacocoReport>().configureEach {
    reports {
      xml.isEnabled = true
    }
  }

  tasks.check {
    dependsOn(tasks.withType<JacocoReport>())
  }

  tasks.withType<Checkstyle>().configureEach {
    reports {
      xml.isEnabled = true
      html.isEnabled = true
    }
  }

  tasks.withType<Test>().configureEach {
    useJUnitPlatform()
  }

  plugins.withType<JavaPlugin>().configureEach {
    configure<JavaPluginExtension> {
      modularity.inferModulePath.set(true)
    }

    val integrationTest by the<SourceSetContainer>().creating

    configurations["integrationTestImplementation"].extendsFrom(configurations["implementation"])
    configurations["integrationTestRuntimeOnly"].extendsFrom(configurations["runtimeOnly"])

    dependencies {
      "integrationTestImplementation"(project(path))
    }

    val integrationTestJarTask = tasks.register<Jar>(integrationTest.jarTaskName) {
      archiveClassifier.set("integration-tests")
      from(integrationTest.output)
    }
    val integrationTestTask = tasks.register<Test>("integrationTest") {
      description = "Runs integration tests."
      group = "verification"

      testClassesDirs = integrationTest.output.classesDirs
      // Make sure we run the 'Jar' containing the tests (and not just the 'classes' folder) so that test resources are also part of the test module
      classpath = configurations[integrationTest.runtimeClasspathConfigurationName] + files(integrationTestJarTask)
      shouldRunAfter("test")
    }

    tasks.named("check") { dependsOn(integrationTestTask) }
  }

  tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:unchecked")
    options.compilerArgs.add("-Xlint:deprecation")
  }
}
