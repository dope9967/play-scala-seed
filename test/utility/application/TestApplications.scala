package utility.application

import com.dimafeng.testcontainers.PostgreSQLContainer
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.{Application, Configuration, Mode}

object TestApplications {

  def basicDatabaseTestApplication(
      container: PostgreSQLContainer,
      evolutionsEnabled: Boolean = true
  ): Application = {
    val configuration: Configuration = Configuration.from(
      Seq(
        Some("slick.dbs.default.profile"     -> "modules.utility.database.ExtendedPostgresProfile$"),
        Some("slick.dbs.default.db.url"      -> container.jdbcUrl),
        Some("slick.dbs.default.db.user"     -> container.username),
        Some("slick.dbs.default.db.password" -> container.password),
        if (!evolutionsEnabled) {
          Some("play.evolutions.db.default.enabled" -> "false")
        } else {
          None
        }
      ).flatten.toMap
    )

    //TODO add Silhouette test bindings

    GuiceApplicationBuilder(configuration = configuration)
      .in(Mode.Test)
      .build()
  }
}
