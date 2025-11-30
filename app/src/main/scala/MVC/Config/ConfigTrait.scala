package MVC.Config

import java.sql.Connection

trait ConfigTrait {
  def getUrl():String
  def getUser():String
  def getPassword():String
  def getConnection():Connection
}
