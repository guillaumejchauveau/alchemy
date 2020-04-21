plugins {
  application
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

application {
  mainClassName = "ovh.gecu.alchemy.cli/ovh.gecu.alchemy.cli.App"
}

tasks.jar {
  manifest {
    attributes(
      "Main-Class" to application.mainClassName
    )
  }
}

dependencies {
  implementation(project(":utils"))
  implementation(project(":core"))
  implementation(project(":lib"))
  runtimeOnly(project(":lib:common"))
  runtimeOnly(project(":lib:integers"))
 implementation(project(":parser"))
  implementation("info.picocli:picocli:4.2.0")
  testImplementation("junit:junit:4.13")
}
