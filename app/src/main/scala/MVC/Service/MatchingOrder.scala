package MVC.Service

import MVC.Model.{Orders, Products, Trade}
import MVC.Repository.{OrderRepo, ProductRepo, TradeRepo}

class MatchingOrder {
  private val orderRepo = new OrderRepo()
  private val tradeRepo = new TradeRepo()
  private val productRepo = new ProductRepo
  def placeOrder(order:Orders): Unit = {
    orderRepo.insertOrders(order)
  }
  def placeProduct(product:Products): Unit = {
    productRepo.insertProducts(product)
  }
  def matchOrder(productId:Int,requiredQty: Int): Unit = {
    val buyOrder = orderRepo.buyOrder(productId)
    val sellOrder = orderRepo.sellOrder(productId)
    val price = productRepo.findPrice(productId)
    var remainingQuantity = requiredQty

    for(buy <- buyOrder;sell <- sellOrder){
//      if(buy.price >= sell.price){
//        val quantityItem = math.min(buy.quantity,sell.quantity)
//        val trade = Trade(0,buy.orderId,sell.orderId,quantityItem,sell.price)
//        tradeRepo.tradeOrder(trade)
//        orderRepo.updateOrder(buy.orderId,buy.quantity-quantityItem)
//        orderRepo.updateOrder(sell.orderId,sell.quantity-quantityItem)
      if(buy.productId == sell.productId && buy.quantity >0 && sell.quantity>0){
        val item = math.min(math.min(buy.quantity,sell.quantity),remainingQuantity)
        val trade = Trade(buy.orderId,sell.orderId,item,price)
        tradeRepo.tradeOrder(trade)
        orderRepo.updateOrder(buy.orderId,productId,buy.quantity-item)
        orderRepo.updateOrder(sell.orderId,productId,sell.quantity-item)
        remainingQuantity -= item
      }
    }
  }
}
