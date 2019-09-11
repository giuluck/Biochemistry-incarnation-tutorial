val defaultSim = "00-minimal"
val defaultTime = 1500.0

plugins {
	java
	eclipse
}

repositories {
	mavenCentral()
}

dependencies {
	compile(group = "it.unibo.alchemist", name = "alchemist", version = "9.0.0")
}

tasks {
	val sim = project.properties["sim"] ?: defaultSim
	val time = project.properties["time"].toString().toDoubleOrNull() ?: defaultTime
	val step = time / 1000.0
	val vars = project.properties["vars"]?.run {
		toString().split("_")
			.map { listOf("-var", it) }
			.flatten()
			.toMutableList()
			.apply { add("-b") }
			.toTypedArray()
	} ?: project.properties["hl"]?.let { arrayOf("-hl") } ?: emptyArray()

	register<JavaExec>("helpAlchemist") {
		classpath = project.sourceSets["main"].runtimeClasspath
		main = "it.unibo.alchemist.Alchemist"
		args("--help")
		dependsOn("compileJava")
	}

	register<JavaExec>("runSimulation") {
		val arguments = arrayOf("-y", "src/main/yaml/$sim.yml",
			"-e", "export/$sim",
			"-t", "$time",
			"-i", "$step")
		classpath = project.sourceSets["main"].runtimeClasspath
		main = "it.unibo.alchemist.Alchemist"
		args(*arguments, *vars)
		dependsOn("compileJava")
	}

	register<Exec>("plotSimulation") {
		commandLine("py", "src/main/python/$sim.py")
		mustRunAfter("runSimulation")
	}

	register<Task>("runAndPlot") {
		dependsOn("runSimulation", "plotSimulation")
	}
}

defaultTasks("runSimulation")