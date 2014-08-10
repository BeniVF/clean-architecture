package anova.payroll.usecases

import anova.payroll.modules.{MemoryPayrollGatewayModule, PayrollModule}
import anova.payroll.usecases.Entities._

import org.scalatest.{FlatSpec, Matchers}

class AddEmployeeSpec extends FlatSpec with Matchers with PayrollModule with MemoryPayrollGatewayModule {
  import AddSEmployee._

  it should "add salaried employee" in {
    val employeeId = 1
    addSalariedEmployee.execute(AddEmployeeRequest(employeeId, "John", "Home", 1000.00))

    val employee = employeeGateway.getEmployee(employeeId).get
    employee.name shouldBe "John"
    employee.classification match {
      case SalariedClassification(salary, MonthlySchedule, HoldMethod) =>
        salary shouldBe 1000.00
      case _ => fail("The employee is not a salaried employee")
    }
  }

  it should "add commissioned employee" in {
    val employeeId = 2
    addCommissionedEmployee.execute(AddEmployeeRequest(employeeId, "Smith", "Home", 5000.00, Option(2.5)))
    val employee = employeeGateway.getEmployee(employeeId).get
    employee.name shouldBe "Smith"
    employee.classification match {
      case CommissionedClassification(salary, commissionRate, BiweeklySchedule, HoldMethod) =>
        salary shouldBe 5000.00
        commissionRate shouldBe 2.5
      case _ => fail("The employee is not a commissioned employee")
    }
  }
}
