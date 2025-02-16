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
    implementation("org.springframework:spring-context:6.2.3")
    implementation("org.springframework:spring-aspects:6.2.3")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
}

tasks.test {
    useJUnitPlatform()
}