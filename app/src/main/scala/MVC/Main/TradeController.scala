package MVC.Main

import MVC.Model.{Orders, Products}
import MVC.Repository.OrderRepo
import MVC.Service.MatchingOrder
import org.slf4j.LoggerFactory

object TradeController {
  def main(args:Array[String]): Unit = {
    val logger = LoggerFactory.getLogger(getClass)
    val service = new MatchingOrder()
    val product = List(
      Products(1, "Laptop", 15000),
      Products(2, "Mobile", 64000),
      Products(3, "Mouse", 80),
      Products(7, "Monitor", 150),
      Products(8, "Keyboard", 220),
    )
    val totalProducts = product.map(service.placeProduct)
    logger.info(s"total products inserted ${totalProducts.size}")
    val orders = List(
      Orders(101,1,"BUY",10,0,"OPEN"),
      Orders(102,1,"SELL",5,0,"OPEN"),
      Orders(103,2,"BUY",18,0,"OPEN"),
      Orders(104,2,"SELL",15,0,"OPEN"),
      Orders(105,3,"BUY",50,0,"OPEN"),
      Orders(106,3,"SELL",40,0,"OPEN"),
      Orders(107,7,"BUY",100,0,"OPEN"),
      Orders(108,7,"SELL",50,0,"OPEN"),
      Orders(109,8,"BUY",70,0,"OPEN"),
      Orders(110,8,"SELL",60,0,"OPEN"),
    )
    val totalInserted = orders.map(service.placeOrder)
    logger.info(s"Total rows inserted: ${totalInserted.size}")
//    val productIds = List(1, 2, 3, 7, 8)
//    val totalTrades = productIds.map(service.matchOrder)
//    logger.info(s"Total trades executed = ${totalTrades.size}")
    val t1 = service.matchOrder(2,3)
    logger.info(s"result trade1 $t1")
    val t2 = service.matchOrder(3,5)
    logger.info(s"result trade2 $t2")

    println("Helloo from Srimanth")
  }
}
