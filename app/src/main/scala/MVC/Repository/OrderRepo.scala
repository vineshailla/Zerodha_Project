package MVC.Repository

import MVC.Config.{DBConnection, Schema}
import MVC.Model.Orders
import org.slf4j.LoggerFactory

import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

class OrderRepo {
  val logger = LoggerFactory.getLogger(getClass)
  val con = new DBConnection
  private val productRepo = new ProductRepo
  val connection = con.getConnection()
  def insertOrders(order:Orders):Int= {
    try {
      val price = productRepo.findPrice(order.productId)
      val calculatedPrice = price * order.quantity
      val statement = connection.prepareStatement(Schema.insertQuery)
      statement.setInt(1, order.orderId)
      statement.setInt(2, order.productId)
      statement.setString(3, order.orderType)
      statement.setInt(4, order.quantity)
      statement.setInt(5, calculatedPrice)
      statement.setString(6, order.status)
      logger.info(s"table is inserted")
      statement.executeUpdate()
    } catch {
      case e: Exception =>
        logger.error(s"Error insert Orders $order",e)
        throw e
    }
  }
  def buyOrder(productId:Int):List[Orders]={
    val ps = connection.prepareStatement(Schema.buy_Query)
    ps.setInt(1,productId)
    val resultSet = ps.executeQuery()
    val order = resultOrder(resultSet)
    if(order.isEmpty){
      throw new NoSuchElementException(s"No seller found for product ID: $productId")
    }
    order
  }
  def sellOrder(productId:Int):List[Orders]= {
    val ps = connection.prepareStatement(Schema.sell_Query)
    ps.setInt(1, productId)
    val resultSet = ps.executeQuery()
    val order = resultOrder(resultSet)
    if (order.isEmpty) {
      throw new NoSuchElementException(s"No Buyyer found for product ID: $productId")
    }
    order
  }
  def updateOrder(orderId: Int, productId: Int, newQty: Int): Int = {
    val price = productRepo.findPrice(productId)
    val newTotal = price * newQty
    val selectPs = connection.prepareStatement(Schema.update_OrderTypeQuery)
    selectPs.setInt(1, orderId)
    val rs = selectPs.executeQuery()
    if (!rs.next()) {
      rs.close()
      selectPs.close()
      throw new RuntimeException("Order not found")
    }
    val orderType = rs.getString("orderType")
    val newStatus = orderType match {
      case "BUY"  => "OPEN"
      case "SELL" => "COMPLETED"
      case _ =>
        rs.close()
        selectPs.close()
        throw new RuntimeException("Invalid order type")
    }
    rs.close()
    selectPs.close()
    val ps = connection.prepareStatement(Schema.update_Query)
    ps.setInt(1, newQty)
    ps.setInt(2, newTotal)
    ps.setString(3, newStatus)
    ps.setInt(4, orderId)
    val result = ps.executeUpdate()
    ps.close()
    result
  }

  private def resultOrder(rs:ResultSet):List[Orders]={
    var order = ListBuffer.empty[Orders]
    while(rs.next()){
      order += Orders(
        rs.getInt("orderId"),
        rs.getInt("productId"),
        rs.getString("orderType"),
        rs.getInt("quantity"),
        rs.getInt("Totalprice"),
        rs.getString("status")
      )
    }
    order.toList
  }
}
