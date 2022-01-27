package io.sdkman.changelogs

import com.github.mongobee.changeset.{ChangeLog, ChangeSet}
import com.mongodb.client.MongoDatabase
import org.bson.Document

@ChangeLog(order = "051")
class JMeterMigration {
  val candidateName  = "jmeter"
  val defaultVersion = "5.4.3"
  @ChangeSet(
    order = "001",
    id = "001-add_jmeter_candidate",
    author = "xshyamx"
  )
  def migration001(implicit db: MongoDatabase): Candidate = {
    Candidate(
      candidate = candidateName,
      name = "Apache JMeter",
      description =
        " The Apache JMeter™ application is open source software, a 100% pure Java application designed to load test functional behavior and measure performance. It was originally designed for testing Web Applications but has since expanded to other test functions.",
      websiteUrl = "https://jmeter.apache.org/"
    ).insert()
  }

  @ChangeSet(
    order = "002",
    id = "002-add_latest_jmeter_candidate_urls",
    author = "xshyamx"
  )
  def migration002(implicit db: MongoDatabase): List[Version] = {
    List(
      defaultVersion,
      "5.4.2"
    ).map(
        version =>
          Version(
            candidate = candidateName,
            version = version,
            url =
              s"https://archive.apache.org/dist/jmeter/binaries/apache-jmeter-$version.zip"
          )
      )
      .validate()
      .insert()
  }
  @ChangeSet(
    order = "003",
    id = "003-set-default-version",
    author = "xshyamx"
  )
  def migration003(implicit db: MongoDatabase): Document = {
    setCandidateDefault(candidateName, defaultVersion)
  }
}
