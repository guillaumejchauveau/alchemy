plugins {
  `java-library`
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
  implementation(project(":core"))
  implementation(project(":utils"))
}
