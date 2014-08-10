package anova.payroll.usecases

import Entities.Employee

class AddSalariedEmployee(implicit employeeGateway: EmployeeGateway) {

  import AddSalariedEmployee._

  def execute(request: AddSalariedEmployeeRequest) =
  this.employeeGateway.saveEmployee(request)

  implicit def toEmployee(request: AddSalariedEmployeeRequest): Employee =
    Employee(
      request.employeeId,
      request.name
    )

}

object AddSalariedEmployee {

  case class AddSalariedEmployeeRequest(employeeId: Long, name: String, address: String, salary: BigDecimal)

}
