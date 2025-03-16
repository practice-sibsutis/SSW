plugins {
    id("java")
}

group = "com.sibsutis.study"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework:spring-test:6.2.4")
    implementation("org.hibernate.orm:hibernate-core:6.6.10.Final")
    implementation("org.postgresql:postgresql:42.7.5")
    implementation("org.springframework.data:spring-data-jpa:3.4.4")
    implementation("org.slf4j:slf4j-api:2.0.17")
    testImplementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")
    implementation("org.hibernate.validator:hibernate-validator:8.0.2.Final")
    testImplementation("org.glassfish:jakarta.el:4.0.2")
    compileOnly("org.hibernate:hibernate-jpamodelgen:6.6.10.Final")
}

tasks.test {
    useJUnitPlatform()
}