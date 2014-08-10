package anova.payroll.usecases

import anova.payroll.usecases.Entities.{MonthlySchedule, SalariedClassification, EmployeeStatus, Employee}

class DeleteEmployeeSpec extends BaseEmployeeSpec {

  override lazy val initialEmployees = List(Employee(1, "James", EmployeeStatus(SalariedClassification(1000.00), MonthlySchedule)))

  it should "delete an existing employee" in {
    val employeeId = 1
    employeeGateway.getEmployee(employeeId) should not be (None)

    deleteEmployee.execute(employeeId)

    employeeGateway.getEmployee(employeeId) shouldBe None
  }

}
