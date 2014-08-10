package anova.payroll.usecases

import anova.payroll.usecases.Entities._

import AddSEmployee._
trait AddEmployee {
  protected def employeeGateway: EmployeeGateway
  def classification(request: AddEmployeeRequest): Classification
  def schedule(request: AddEmployeeRequest): Schedule

  def execute(request: AddEmployeeRequest) =
    this.employeeGateway.saveEmployee(request)

  implicit def toEmployee(request: AddEmployeeRequest): Employee =
    Employee(
      request.employeeId,
      request.name,
      EmployeeStatus(
        classification = classification(request),
        schedule = schedule(request)
      )
    )
}

class AddSEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): Classification = SalariedClassification(request.salary.get)
  def schedule(request: AddEmployeeRequest): Schedule = MonthlySchedule
}

class AddCommissionedEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): Classification = CommissionedClassification(request.salary.get, request.commissionRate.get)
  def schedule(request: AddEmployeeRequest): Schedule = BiweeklySchedule
}
class AddHourlyEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): Classification = HourlyClassification(request.hourlyRate.get)
  def schedule(request: AddEmployeeRequest): Schedule = WeeklySchedule
}

object AddSEmployee {
  case class AddEmployeeRequest(employeeId: Long, name: String, address: String, salary: Option[BigDecimal] = None,
                                commissionRate: Option[BigDecimal] = None, hourlyRate: Option[BigDecimal] = None)
}
