plugins {
  `java-library`
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
  runtimeOnly("org.fusesource.jansi:jansi:1.18")
  api("org.apache.logging.log4j:log4j-core:2.13.1")
  testImplementation("junit:junit:4.13")
}
