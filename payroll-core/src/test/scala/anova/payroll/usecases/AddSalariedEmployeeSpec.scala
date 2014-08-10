package anova.payroll.usecases

import anova.payroll.modules.{MemoryPayrollGatewayModule, PayrollModule}

import org.scalatest.{FlatSpec, Matchers}

class AddSalariedEmployeeSpec extends FlatSpec with Matchers with PayrollModule with MemoryPayrollGatewayModule {
  import AddSalariedEmployee._

  it should "add salaried employee" in {
    val employeeId = 1
    addSalariedEmployee.execute(AddSalariedEmployeeRequest(employeeId, "John", "Home", 1000.00))

    val employee = employeeGateway.getEmployee(employeeId).get
    employee.name shouldBe "John"
  }
}
