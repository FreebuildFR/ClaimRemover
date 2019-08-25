val Name = "ClaimRemover"
val Version = "0.0.1-SNAPSHOT"
val GroupId = "fr.freebuild"

name := Name
version := Version
organization := GroupId

scalaVersion := "2.13.0"
scalacOptions += "-language:implicitConversions"

packageOptions in (Compile, packageBin) +=
    Package.ManifestAttributes("Automatic-Module-Name" -> (GroupId + "." + Name.toLowerCase))

/* uncomment if you need more dependencies*/
resolvers += Resolver.mavenCentral

resolvers += "jitpack" at "https://jitpack.io"
resolvers += "spigot-repo" at "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"
resolvers += "redprotect-repo" at "https://raw.githubusercontent.com/FabioZumbi12/RedProtect/mvn-repo/"
resolvers += "keyle-repo" at "https://nexus.keyle.de/content/groups/public/"

libraryDependencies += "org.bukkit" % "bukkit" % "1.14.4-R0.1-SNAPSHOT" % "provided"
libraryDependencies += "com.github.Jannyboy11.ScalaPluginLoader" % "ScalaLoader" % "v0.11.1" % "provided"
libraryDependencies ++= Seq(
    "br.net.fabiozumbi12.RedProtect" % "RedProtect-Core" % "7.6.2" % "provided" intransitive(),
    "br.net.fabiozumbi12.RedProtect" % "RedProtect-Spigot" % "7.6.2" % "provided" intransitive(),
    "com.github.ruany" % "LiteBansAPI" % "0.3" % "provided" intransitive()
)

/* uncomment if you need to shade dependencies - see https://github.com/sbt/sbt-assembly#shading
assemblyShadeRules in assembly := Seq(
    ShadeRule.rename("xyz.janboerman.guilib.**" -> "claimremover.guilib.@1").inAll,
)
*/
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
assemblyMergeStrategy in assembly := {
    case "plugin.yml"   => MergeStrategy.first /* always choose our own plugin.yml if we shade other plugins */
    case x              =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
}
assemblyJarName in assembly := Name + "-" + Version + ".jar"
