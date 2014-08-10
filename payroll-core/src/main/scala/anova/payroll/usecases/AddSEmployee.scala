package anova.payroll.usecases

import anova.payroll.usecases.Entities.{CommissionedClassification, SalariedClassification, Classification, Employee}

import AddSEmployee._
trait AddEmployee {
  protected def employeeGateway: EmployeeGateway
  def classification(request: AddEmployeeRequest): Classification

  def execute(request: AddEmployeeRequest) =
    this.employeeGateway.saveEmployee(request)

  implicit def toEmployee(request: AddEmployeeRequest): Employee =
    Employee(
      request.employeeId,
      request.name,
      classification(request)
    )
}

class AddSEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): Classification = SalariedClassification(request.salary)
}

class AddCommissionedEmployee(implicit val employeeGateway: EmployeeGateway) extends AddEmployee {
  def classification(request: AddEmployeeRequest): Classification = CommissionedClassification(request.salary, request.commissionRate.get)
}

object AddSEmployee {
  case class AddEmployeeRequest(employeeId: Long, name: String, address: String, salary: BigDecimal, commissionRate: Option[BigDecimal]=None)
}
