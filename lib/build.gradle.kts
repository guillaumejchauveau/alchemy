publishing {
  publications {
    create<MavenPublication>("lib") {
      from(components["java"])
    }
  }

  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/guillaumejchauveau/alchemy")
      credentials {
        username = System.getenv("USERNAME")
        password = System.getenv("TOKEN")
      }
    }
  }
}

dependencies {
  implementation(project(":core"))
  implementation("com.speedment.common:tuple:3.2.9")
  implementation("org.apache.logging.log4j:log4j-core:2.13.1")
  runtimeOnly("org.fusesource.jansi:jansi:1.18")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
  integrationTestImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
  integrationTestRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}
