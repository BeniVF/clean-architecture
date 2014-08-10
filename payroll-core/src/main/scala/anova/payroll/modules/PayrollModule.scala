package anova.payroll.modules

import anova.payroll.usecases._

import scala.collection.concurrent.TrieMap

trait PayrollModule {
  implicit def employeeGateway : EmployeeGateway
  lazy val addSalariedEmployee = new AddSEmployee
  lazy val addCommissionedEmployee = new AddCommissionedEmployee
  lazy val addHourlyEmployee = new AddHourlyEmployee
}

trait MemoryPayrollGatewayModule {
  implicit lazy val employeeGateway : EmployeeGateway = new MemoryEmployeeGateway(TrieMap())
}