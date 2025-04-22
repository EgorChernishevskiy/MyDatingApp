plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {

	// Spring
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-configuration-processor:3.4.3")

	implementation("io.github.cdimascio:java-dotenv:5.2.2")


	// PostgreSQL
	implementation("org.postgresql:postgresql:42.7.2")

	// location
	implementation("org.hibernate.orm:hibernate-spatial:6.4.4.Final")
	implementation("org.locationtech.jts:jts-core:1.19.0")

	//Redis
	implementation ("org.springframework.boot:spring-boot-starter-data-redis")
	implementation ("org.springframework.boot:spring-boot-starter-cache")


	// AmazonS3
	implementation("com.amazonaws:aws-java-sdk-s3:1.12.780")

	// Lombok
	compileOnly ("org.projectlombok:lombok:1.18.30")
	testCompileOnly ("org.projectlombok:lombok:1.18.30")
	annotationProcessor ("org.projectlombok:lombok:1.18.30")
	testAnnotationProcessor ("org.projectlombok:lombok:1.18.30")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
