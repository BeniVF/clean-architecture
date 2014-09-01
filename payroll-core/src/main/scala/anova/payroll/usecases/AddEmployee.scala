package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.Data._

import AddSalariedEmployee._
import anova.payroll.usecases.EntitiesBuilder.{EmployeePaymentBuilder, EmployeeBuilder}

trait AddEmployee {
  protected def employeeGateway: EmployeeGateway
  def classification(request: AddEmployeeRequest): PaymentClassification
  def schedule(request: AddEmployeeRequest): PaymentSchedule

  def execute(request: AddEmployeeRequest) =
    this.employeeGateway.saveEmployee(request)

  implicit def toEmployee(request: AddEmployeeRequest): Employee =
    EmployeeBuilder(
      request.employeeId,
      request.name,
      EmployeePaymentBuilder(
        classification = classification(request),
        schedule = schedule(request)
      )
    )
}

class AddSalariedEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): PaymentClassification = SalariedClassification(request.salary.get)
  def schedule(request: AddEmployeeRequest): PaymentSchedule = MonthlySchedule
}

class AddCommissionedEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): PaymentClassification = CommissionedClassification(request.salary.get, request.commissionRate.get)
  def schedule(request: AddEmployeeRequest): PaymentSchedule = BiweeklySchedule
}
class AddHourlyEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): PaymentClassification = HourlyClassification(request.hourlyRate.get)
  def schedule(request: AddEmployeeRequest): PaymentSchedule = WeeklySchedule
}

object AddSalariedEmployee {
  case class AddEmployeeRequest(employeeId: Long, name: String, address: String, salary: Option[BigDecimal] = None,
                                commissionRate: Option[BigDecimal] = None, hourlyRate: Option[BigDecimal] = None)
}
