package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.Data._

import AddSalariedEmployee._
import anova.payroll.usecases.DataBuilder.EmployeeBuilder

trait AddEmployee {
  protected def employeeGateway: EmployeeGateway

  def classification(request: AddEmployeeRequest): PaymentClassificationData

  def schedule(request: AddEmployeeRequest): PaymentScheduleData

  def execute(request: AddEmployeeRequest) =
    this.employeeGateway.saveEmployee(request)

  implicit def toEmployee(request: AddEmployeeRequest): EmployeeData =
    EmployeeBuilder(
      request.employeeId,
      request.name,
      classification = classification(request),
      schedule = schedule(request)
    )
}

class AddSalariedEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): PaymentClassificationData = SalariedClassificationData(request.salary.get)

  def schedule(request: AddEmployeeRequest): PaymentScheduleData = MonthlyScheduleData
}

class AddCommissionedEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): PaymentClassificationData = CommissionedClassificationData(request.salary.get, request.commissionRate.get)

  def schedule(request: AddEmployeeRequest): PaymentScheduleData = BiweeklyScheduleData
}

class AddHourlyEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): PaymentClassificationData = HourlyClassificationData(request.hourlyRate.get)

  def schedule(request: AddEmployeeRequest): PaymentScheduleData = WeeklyScheduleData
}

object AddSalariedEmployee {

  case class AddEmployeeRequest(employeeId: Long, name: String, address: String, salary: Option[BigDecimal] = None,
                                commissionRate: Option[BigDecimal] = None, hourlyRate: Option[BigDecimal] = None)

}
