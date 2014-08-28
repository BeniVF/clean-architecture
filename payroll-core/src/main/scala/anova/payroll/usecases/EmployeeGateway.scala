package anova.payroll.usecases

import anova.payroll.usecases.Entities.{TimeCard, Employee}

import scala.collection.concurrent.TrieMap
import org.joda.time.DateTime

trait EmployeeGateway {
  def getEmployee(employeeId: Long): Option[Employee]

  def saveEmployee(employee: Employee)

  def deleteEmployee(employeeId: Long)

  def addTimeCard(employeeId: Long, timeCard: TimeCard)

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCard]

}

class MemoryEmployeeGateway(implicit initialEmployees: List[Employee]) extends EmployeeGateway {
  protected lazy val employees = toTrieMap(initialEmployees)
  protected lazy val timeCards = TrieMap[Long, Map[DateTime, TimeCard]]()

  private def toTrieMap(templates: List[Employee]): TrieMap[Long, Employee] = {
    val ids = templates.map(_.employeeId)
    val result = TrieMap[Long, Employee]()
    (ids zip templates).toSeq.foreach(result += _)
    result
  }

  def addTimeCard(employeeId: Long, timeCard: TimeCard) = {
    val employeeTimeCards = timeCards.get(employeeId).getOrElse(Map[DateTime, TimeCard]())
    timeCards += (employeeId -> (employeeTimeCards + (timeCard.date -> timeCard)))
  }

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCard] =
    timeCards.get(employeeId).map(timeCards => timeCards.get(date)).flatten

  def getEmployee(employeeId: Long): Option[Employee] = employees.get(employeeId)

  def saveEmployee(employee: Employee) = employees += (employee.employeeId -> employee)

  def deleteEmployee(employeeId: Long) = employees -= employeeId
}