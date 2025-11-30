package MVC.Repository

import MVC.Config.{DBConnection, Schema}
import MVC.Model.Trade
import org.slf4j.LoggerFactory

class TradeRepo {
  val conn = new DBConnection
  val logger = LoggerFactory.getLogger(getClass)
  val connection = conn.getConnection()
  def tradeOrder(trade:Trade):Int= {
    try {
      val statement = connection.prepareStatement(Schema.insertTrade)
      statement.setInt(1, trade.buyOrderId)
      statement.setInt(2, trade.sellOrderId)
      statement.setInt(3, trade.quantity)
      statement.setInt(4, trade.price)
      statement.executeUpdate()
    } catch {
      case e: Exception =>
        logger.error(s"error insert trades $trade", e)
        throw e
    }
  }
}
