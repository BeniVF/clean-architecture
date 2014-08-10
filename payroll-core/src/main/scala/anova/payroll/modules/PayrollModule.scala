package anova.payroll.modules

import anova.payroll.usecases.{MemoryEmployeeGateway, EmployeeGateway, AddSalariedEmployee}

import scala.collection.concurrent.TrieMap

trait PayrollModule {
  implicit def employeeGateway : EmployeeGateway
  lazy val addSalariedEmployee = new AddSalariedEmployee
}

trait MemoryPayrollGatewayModule {
  implicit lazy val employeeGateway : EmployeeGateway = new MemoryEmployeeGateway(TrieMap())
}