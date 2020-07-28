publishing {
  publications {
    create<MavenPublication>("core") {
      from(components["java"])
    }
  }

  repositories {
    maven {
      name = "GitHubPackages"
      url = uri("https://maven.pkg.github.com/guillaumejchauveau/alchemy")
      credentials {
        username = System.getenv("USERNAME")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}
