# HOW-TO

To run the desired example, launch `gradle -Psimulation=THESIMULATIONFILE`, where `THESIMULATIONFILE` must be the name of a valid Alchemist YAML file in `src/main/yaml`. For instance, to launch the first example, use this command:

``gradlew -Psimulation=00-minimal``

If an effects file with the same name of the YAML file is present in the effects folder, it will be loaded automatically.

Some tutorial simulations are already in `src/main/yaml`, you can add your own to make tests and learn Alchemist Biochemistry.
