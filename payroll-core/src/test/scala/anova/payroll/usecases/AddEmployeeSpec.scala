package anova.payroll.usecases

import anova.payroll.usecases.Entities._
import anova.payroll.usecases.EntitiesBuilder.{EmployeePaymentBuilder, EmployeeBuilder}

class AddEmployeeSpec extends BaseEmployeeSpec {

  import anova.payroll.usecases.AddSEmployee._

  it should "add a salaried employee" in {
    val employeeId = 1
    val request = AddEmployeeRequest(employeeId, "John", "Home", salary = Option(1000.00))
    addSalariedEmployee.execute(request)

    val employee = employeeGateway.getEmployee(employeeId).get

    employee shouldBe EmployeeBuilder(
      employeeId, request.name,
      EmployeePaymentBuilder(
        SalariedClassification(request.salary.get),
        MonthlySchedule,
        HoldMethod)
    )
  }

  it should "add a commissioned employee" in {
    val employeeId = 2
    val request = AddEmployeeRequest(employeeId, "Smith", "Home", salary = Option(5000.00),
      commissionRate = Option(2.5))
    addCommissionedEmployee.execute(request)

    val employee = employeeGateway.getEmployee(employeeId).get

    employee shouldBe EmployeeBuilder(
      employeeId, request.name,
      EmployeePaymentBuilder(
        CommissionedClassification(request.salary.get, request.commissionRate.get),
        BiweeklySchedule,
        HoldMethod
      )
    )
  }

  it should "add an hourly employee" in {
    val employeeId = 2
    val request = AddEmployeeRequest(employeeId, "Paul", "Home", hourlyRate = Option(15.25))
    addHourlyEmployee.execute(request)

    val employee = employeeGateway.getEmployee(employeeId).get

    employee shouldBe EmployeeBuilder(
      employeeId, request.name,
      EmployeePaymentBuilder(
        HourlyClassification(request.hourlyRate.get),
        WeeklySchedule,
        HoldMethod
      )
    )
  }
}
