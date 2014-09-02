package anova.payroll.usecases

import anova.payroll.usecases.Data._
import anova.payroll.usecases.DataBuilder.EmployeeBuilder

class AddEmployeeSpec extends BaseEmployeeSpec {

  import anova.payroll.usecases.AddSalariedEmployee._

  it should "add a salaried employee" in {
    val employeeId = 1
    val request = AddEmployeeRequest(employeeId, "John", "Home", salary = Option(1000.00))
    addSalariedEmployee.execute(request)

    val employee = employeeGateway.getEmployee(employeeId).get

    employee shouldBe EmployeeBuilder(
      employeeId, request.name,
      SalariedClassificationData(request.salary.get),
      MonthlyScheduleData,
      HoldMethodData
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
      CommissionedClassificationData(request.salary.get, request.commissionRate.get),
      BiweeklyScheduleData,
      HoldMethodData

    )
  }

  it should "add an hourly employee" in {
    val employeeId = 2
    val request = AddEmployeeRequest(employeeId, "Paul", "Home", hourlyRate = Option(15.25))
    addHourlyEmployee.execute(request)

    val employee = employeeGateway.getEmployee(employeeId).get

    employee shouldBe EmployeeBuilder(
      employeeId, request.name,
      HourlyClassificationData(request.hourlyRate.get),
      WeeklyScheduleData,
      HoldMethodData
    )
  }
}
