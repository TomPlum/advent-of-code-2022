dependencies {
    "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.9.1")
    "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.9.1")
    "testImplementation"("org.junit.jupiter:junit-jupiter-params:5.9.1")
    "testImplementation"("org.junit.platform:junit-platform-launcher:1.9.1")
    "testImplementation"("com.willowtreeapps.assertk:assertk-jvm:0.25")
}

tasks.withType<Test> {
    useJUnitPlatform { }
}