package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.Entities.{CommissionedClassification, SalesReceipt}
import org.joda.time.DateTime

class PostSalesReceipt(implicit val employeeGateway: EmployeeGateway) {
  import PostSalesReceipt._
  def execute(request: PostSalesReceiptRequest) = {
    val employee = employeeGateway.getEmployee(request.employeeId)
    employee.map {
      employee =>
      employee.employeePayment.classification match {
        case classification: CommissionedClassification =>
          employeeGateway.addSalesReceipt(request.employeeId, SalesReceipt(request.date, request.amount))
        case _ => //TODO Not commissioned employee??
      }
    }
    //TODO Non existing employee??
  }

}

object PostSalesReceipt {
  case class PostSalesReceiptRequest(employeeId: Long, date:DateTime, amount: BigDecimal)
}
