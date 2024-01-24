import Dependencies.Version
import Dependencies.VersionString
object Config:
    val organization = valueStr("TOOLKIT_ORG", "org.scala-lang")
    val crossVersions = List("3", "2.13")
    val platforms = List(Platform.jvm, Platform.js, Platform.native)
    val changelogDir = valuePath("CHANGELOG_DIR", os.pwd / "changelog")
    val releaseVersion = valueVersion("TOOLKIT_VERSION_RELEASE", Version(0,2,1))
    val developmentVersion = valueVersion("TOOLKIT_VERSION_DEVELOPMENT", Version(0,2,2))

    private def valueVersion = value[Version](_, _, Version.parse(_).getOrElse(throw IllegalArgumentException("Invalid version")))
    private def valueStr = value[String](_, _, identity)
    private def valuePath = value[os.Path](_, _, os.Path(_))
    private def value[T](env: String, default: T, mapper: String => T): T = 
        val v = System.getenv(env)
        if v == null || v.isEmpty then default else mapper(v)

    private object Platform:
        val js = ("js", Some("js"), Some("sjs1"))
        val native = ("native", Some("native"), Some("native0.4"))
        val jvm = ("jvm", None, None)

