import play.sbt.PlayImport.*
import sbt.*

object AppDependencies {
  lazy val bootStrapPlayVersion = "9.8.0"

  lazy val compile: Seq[ModuleID] = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-frontend-play-30" % bootStrapPlayVersion
  )

  lazy val test: Seq[ModuleID] = Seq(
    "uk.gov.hmrc" %% "bootstrap-test-play-30"     % bootStrapPlayVersion,
    "org.pegdown" %  "pegdown"                    % "1.6.0"
  ).map(_ % Test)
}
