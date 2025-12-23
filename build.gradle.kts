plugins {
    id("java")
}

group = "com.example.appiumpoc.test"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.testng:testng:7.9.0")
    implementation("io.appium:java-client:9.1.0")
    implementation("com.aventstack:extentreports:5.1.1")
    implementation("commons-io:commons-io:2.14.0")
    implementation("org.slf4j:slf4j-simple:2.0.12")
}

tasks.test {
    useTestNG() {
        suites("testng.xml")
    }
    systemProperties = System.getProperties().mapKeys { it.key.toString() }.mapValues { it.value.toString() }
}
