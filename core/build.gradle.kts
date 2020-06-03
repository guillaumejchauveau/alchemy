plugins {
  `java-library`
  `java-test-fixtures`
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  integrationTestImplementation(testFixtures(project(path)))
  integrationTestImplementation(project(":lib"))
  integrationTestImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
  integrationTestRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
