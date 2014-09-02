package anova.payroll.usecases

import anova.payroll.usecases.DataBuilder.EmployeeBuilder


class DeleteEmployeeSpec extends BaseEmployeeSpec {

  override lazy val initialEmployees = List(EmployeeBuilder(1, "James"), EmployeeBuilder(2, "Arthur"))

  it should "delete an existing employee" in {
    val employeeId = 1
    employeeGateway.getEmployee(employeeId) shouldBe initialEmployees.headOption

    deleteEmployee.execute(employeeId)

    employeeGateway.getEmployee(employeeId) shouldBe None
  }

}
