plugins {
  `java-library`
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
  implementation(project(":utils"))
  implementation(project(":core"))
  testImplementation("junit:junit:4.13")
}

subprojects {
  group = "ovh.gecu.alchemy.lib"
}
