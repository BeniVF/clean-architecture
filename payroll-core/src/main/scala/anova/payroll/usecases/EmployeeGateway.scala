package anova.payroll.usecases

import Entities.Employee

import scala.collection.concurrent.TrieMap

trait EmployeeGateway {
  def getEmployee(employeeId: Long): Option[Employee]
  def saveEmployee(employee: Employee)
}
class MemoryEmployeeGateway(employees: TrieMap[Long, Employee]) extends EmployeeGateway {
  def getEmployee(employeeId: Long): Option[Employee] = employees.get(employeeId)
  def saveEmployee(employee: Employee) = employees += (employee.employeeId -> employee)

}