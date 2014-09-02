package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.PostTimeCards.PostTimeCardsToEmployeeRequest
import anova.payroll.usecases.Data._
import org.joda.time.DateTime


class PostTimeCards(implicit val employeeGateway: EmployeeGateway) {
  def execute(request: PostTimeCardsToEmployeeRequest) = {
    val employee = employeeGateway.getEmployee(request.employeeId)

    employee.map {
      employee =>
      employee.classification match {
        case classification: HourlyClassificationData =>
          employeeGateway.addTimeCard(request.employeeId, TimeCardData(request.dateTime, request.hours))
        case _ => //TODO Not hourly employee??
      }
    }
      //TODO Non existing employee??
  }

}

object PostTimeCards {
  case class PostTimeCardsToEmployeeRequest(dateTime: DateTime, hours: BigDecimal, employeeId: Long)
}