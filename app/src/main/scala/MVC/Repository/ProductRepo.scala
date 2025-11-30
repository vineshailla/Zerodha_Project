package MVC.Repository

import MVC.Config.{DBConnection, Schema}
import MVC.Model.Products
import org.slf4j.LoggerFactory

class ProductRepo {
  val logger = LoggerFactory.getLogger(getClass)
  val conn = new DBConnection
  val connection = conn.getConnection()
  def insertProducts(product:Products):Int= {
    try {
      val statement = connection.prepareStatement(Schema.product_Query)
      statement.setInt(1, product.productId)
      statement.setString(2, product.item)
      statement.setInt(3, product.price)
      statement.executeUpdate()
    }
    catch {
      case ex: Exception =>
        logger.error(s"Error inserting product $product", ex)
        throw ex
    }
  }
  def findPrice(productId:Int):Int={
    val preparedStatement = connection.prepareStatement(Schema.product_price_Query)
    preparedStatement.setInt(1,productId)
    val rs = preparedStatement.executeQuery()
    if(rs.next()){
      rs.getInt("price")
    }
    else
      throw new RuntimeException("Product not found")
  }
}
