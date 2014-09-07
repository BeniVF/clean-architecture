package anova.payroll.modules

import anova.payroll.gateway.{EmployeeGateway, MemoryEmployeeGateway}
import anova.payroll.usecases.Data._
import anova.payroll.usecases._

trait PayrollModule {
  implicit def employeeGateway : EmployeeGateway
  lazy val addSalariedEmployee = new AddSalariedEmployee
  lazy val addCommissionedEmployee = new AddCommissionedEmployee
  lazy val addHourlyEmployee = new AddHourlyEmployee
  lazy val deleteEmployee = new DeleteEmployee
  lazy val postTimeCardsToEmployee = new PostTimeCards
  lazy val postSalesReceipt = new PostSalesReceipt
  lazy val payDay = new PayDay
}

trait MemoryPayrollGatewayModule {
  implicit lazy val initialEmployees: List[EmployeeData] = List()
  implicit lazy val employeeGateway : EmployeeGateway = new MemoryEmployeeGateway
}