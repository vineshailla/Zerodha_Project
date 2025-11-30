package MVC.Config

object Schema {
  val insertQuery =
    """
        INSERT INTO Orders(orderId,productId,orderType,quantity,Totalprice,status) VALUES (?,?,?,?,?,?)
        """.stripMargin
  val buy_Query = "SELECT * FROM Orders WHERE productId=? AND orderType='BUY' AND status='OPEN'"
  val sell_Query = "SELECT * FROM Orders WHERE productId=? AND orderType='SELL' AND status='OPEN'"
  val update_Query = "UPDATE Orders SET quantity=?, TotalPrice=?,status=? WHERE orderId=?"
  val update_OrderTypeQuery = "SELECT orderType FROM Orders WHERE orderId=?"
  val product_Query =
    """ INSERT INTO Products(productId,item,price) VALUES (?,?,?)
      |""".stripMargin
  val product_price_Query =  "SELECT price FROM Products WHERE productId=?"
  val insertTrade =
    """ INSERT INTO Trades(buyOrderId, sellOrderId, quantity,price) VALUES (?,?,?,?)
      |""".stripMargin

}
