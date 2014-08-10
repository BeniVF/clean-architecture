package anova.payroll.usecases

import anova.payroll.modules.{MemoryPayrollGatewayModule, PayrollModule}
import anova.payroll.usecases.Entities._

import org.scalatest.{FlatSpec, Matchers}

class AddSalariedEmployeeSpec extends FlatSpec with Matchers with PayrollModule with MemoryPayrollGatewayModule {
  import AddSalariedEmployee._

  it should "add salaried employee" in {
    val employeeId = 1
    addSalariedEmployee.execute(AddSalariedEmployeeRequest(employeeId, "John", "Home", 1000.00))

    val employee = employeeGateway.getEmployee(employeeId).get
    employee.name shouldBe "John"
    employee.classification match {
      case salaried: SalariedClassification =>
        salaried.salary shouldBe 1000.00
        salaried.schedule match {
          case MonthlySchedule =>
          case _=> fail("The employee does not have the monthly schedule")
        }
        salaried.method match {
          case HoldMethod =>
          case _=> fail("The employee does not have the hold method")
        }
      case _ => fail("The employee is not a salaried employee")
    }
  }
}
