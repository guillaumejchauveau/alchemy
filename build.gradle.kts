allprojects {
  group = "ovh.gecu.alchemy"
  version = "2.0"
  repositories {
    mavenCentral()
  }
}

plugins {
  base
  jacoco
  id("org.javamodularity.moduleplugin") version "1.6.0" apply false
}

tasks.clean {
  subprojects.forEach {
    val cleanTask = it.tasks.findByName("clean")
    if (cleanTask != null) {
      dependsOn(cleanTask)
    }

    it.tasks.withType<JavaCompile>().configureEach {
      options.compilerArgs.add("-Xlint:unchecked")
      options.compilerArgs.add("-Xlint:deprecation")
    }
  }
}


subprojects {
  apply(plugin = "jacoco")
  apply(plugin = "java")
  apply(plugin = "org.javamodularity.moduleplugin")

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

  /*task("writePom") {
    doLast {
      maven.pom {
      }.writeTo("pom.xml")
    }
  }*/
}
