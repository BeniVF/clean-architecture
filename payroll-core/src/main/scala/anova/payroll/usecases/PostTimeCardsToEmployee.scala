package anova.payroll.usecases

import anova.payroll.usecases.PostTimeCardsToEmployee.PostTimeCardsToEmployeeRequest
import anova.payroll.usecases.Entities.{TimeCard, HourlyClassification}
import org.joda.time.DateTime


class PostTimeCardsToEmployee(implicit val employeeGateway: EmployeeGateway) {
  def execute(request: PostTimeCardsToEmployeeRequest) = {
    val employee = employeeGateway.getEmployee(request.employeeId)

    if (employee.isDefined) {
      employee.get.employeePayment.classification match {
        case classification: HourlyClassification =>
          employeeGateway.addTimeCard(request.employeeId, TimeCard(request.dateTime, request.hours))
        case _ => //TODO Not hourly employee??
      }
    } else {
      //TODO Non existing employee??
    }
  }

}

object PostTimeCardsToEmployee {
  case class PostTimeCardsToEmployeeRequest(dateTime: DateTime, hours: BigDecimal, employeeId: Long)
}