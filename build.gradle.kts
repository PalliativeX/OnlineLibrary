import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.1.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.72"
	kotlin("plugin.spring") version "1.3.72"
	kotlin("plugin.jpa") version "1.3.72"
}

group = "com.base"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.3.1.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.3.1.RELEASE")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-data-rest:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:2.9.2")
	implementation("org.springframework.boot:spring-boot-starter-web:2.3.1.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-security:2.3.1.RELEASE")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.11.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.72")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.72")
	runtimeOnly("mysql:mysql-connector-java:8.0.20")

	testImplementation("org.springframework.security:spring-security-test:5.3.3.RELEASE")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.3.1.RELEASE")
	testImplementation("io.kotest:kotest-runner-junit5-jvm:4.1.3") // for kotest framework
	testImplementation("io.kotest:kotest-assertions-core-jvm:4.1.3") // for kotest core jvm assertions
	testImplementation("io.kotest:kotest-property-jvm:4.1.3") // for kotest property test
	testImplementation("io.kotest:kotest-runner-console-jvm:4.1.3")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
