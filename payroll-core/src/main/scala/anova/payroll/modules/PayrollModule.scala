package anova.payroll.modules

import anova.payroll.usecases.{AddCommissionedEmployee, MemoryEmployeeGateway, EmployeeGateway, AddSEmployee}

import scala.collection.concurrent.TrieMap

trait PayrollModule {
  implicit def employeeGateway : EmployeeGateway
  lazy val addSalariedEmployee = new AddSEmployee
  lazy val addCommissionedEmployee = new AddCommissionedEmployee
}

trait MemoryPayrollGatewayModule {
  implicit lazy val employeeGateway : EmployeeGateway = new MemoryEmployeeGateway(TrieMap())
}