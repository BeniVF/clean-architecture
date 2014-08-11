package anova.payroll.modules

import anova.payroll.usecases.Entities.Employee
import anova.payroll.usecases._

trait PayrollModule {
  implicit def employeeGateway : EmployeeGateway
  lazy val addSalariedEmployee = new AddSEmployee
  lazy val addCommissionedEmployee = new AddCommissionedEmployee
  lazy val addHourlyEmployee = new AddHourlyEmployee
  lazy val deleteEmployee = new DeleteEmployee
  lazy val postTimeCardsToEmployee = new PostTimeCardsToEmployee
}

trait MemoryPayrollGatewayModule {
  implicit lazy val initialEmployees: List[Employee] = List()
  implicit lazy val employeeGateway : EmployeeGateway = new MemoryEmployeeGateway
}