package MVC.Config

import com.typesafe.config.{Config, ConfigFactory}
import org.slf4j.LoggerFactory

import java.sql.{Connection, DriverManager}

class DBConnection extends ConfigTrait {
  val logger = LoggerFactory.getLogger(getClass)
  private val config:Config = ConfigFactory.load()
  private val dataConfig = config.getConfig("database")

  override def getUrl(): String = dataConfig.getString("url")

  override def getUser(): String = dataConfig.getString("userName")

  override def getPassword(): String = dataConfig.getString("password")

  override def getConnection(): Connection = {
    val con = DriverManager.getConnection(getUrl(),getUser(),getPassword())
    logger.info(s"connection established")
    con
  }
}
