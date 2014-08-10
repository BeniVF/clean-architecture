package anova.payroll.usecases

import Entities.Employee

import scala.collection.concurrent.TrieMap

trait EmployeeGateway {
  def getEmployee(employeeId: Long): Option[Employee]
  def saveEmployee(employee: Employee)
  def deleteEmployee(employeeId: Long)
}
class MemoryEmployeeGateway(implicit initialEmployees: List[Employee]) extends EmployeeGateway {
  lazy val employees = toTrieMap(initialEmployees)

  private def toTrieMap(templates: List[Employee]): TrieMap[Long, Employee] = {
    val ids = templates.map(_.employeeId)
    val result = TrieMap[Long, Employee]()
    (ids zip templates).toSeq.foreach(result += _)
    result
  }

  def getEmployee(employeeId: Long): Option[Employee] = employees.get(employeeId)
  def saveEmployee(employee: Employee) = employees += (employee.employeeId -> employee)
  def deleteEmployee(employeeId: Long) = employees -= employeeId
}