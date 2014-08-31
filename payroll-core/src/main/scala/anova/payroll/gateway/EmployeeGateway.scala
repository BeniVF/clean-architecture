package anova.payroll.gateway

import anova.payroll.usecases.Entities.{Employee, SalesReceipt, TimeCard}
import org.joda.time.DateTime

import scala.collection.concurrent.TrieMap

trait EmployeeGateway {
  def getEmployee(employeeId: Long): Option[Employee]

  def saveEmployee(employee: Employee)

  def deleteEmployee(employeeId: Long)

  def addTimeCard(employeeId: Long, timeCard: TimeCard)

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCard]

  def addSalesReceipt(employeeId: Long, salesReceipt: SalesReceipt)

  def getSalesReceipt(employeeId: Long, date: DateTime): Option[SalesReceipt]

}

class MemoryEmployeeGateway(implicit initialEmployees: List[Employee]) extends EmployeeGateway {
  protected lazy val employees = toTrieMap(initialEmployees)
  protected lazy val timeCards = TrieMap[Long, Map[DateTime, TimeCard]]()
  protected lazy val salesReceipts = TrieMap[Long, Map[DateTime, SalesReceipt]]()

  private def toTrieMap(templates: List[Employee]): TrieMap[Long, Employee] = {
    val ids = templates.map(_.employeeId)
    val result = TrieMap[Long, Employee]()
    (ids zip templates).toSeq.foreach(result += _)
    result
  }

  def addTimeCard(employeeId: Long, timeCard: TimeCard) {
    val employeeTimeCards = timeCards.get(employeeId).getOrElse(Map[DateTime, TimeCard]())
    timeCards += (employeeId -> (employeeTimeCards + (timeCard.date -> timeCard)))
  }

  def addSalesReceipt(employeeId: Long, salesReceipt: SalesReceipt) = {
    val employeeTimeCards = salesReceipts.get(employeeId).getOrElse(Map[DateTime, SalesReceipt]())
    salesReceipts += (employeeId -> (employeeTimeCards + (salesReceipt.date -> salesReceipt)))
  }

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCard] =
    timeCards.get(employeeId).map(timeCards => timeCards.get(date)).flatten

  def getSalesReceipt(employeeId: Long, date: DateTime): Option[SalesReceipt] =
    salesReceipts.get(employeeId).map(salesReceipts => salesReceipts.get(date)).flatten

  def getEmployee(employeeId: Long): Option[Employee] = employees.get(employeeId)

  def saveEmployee(employee: Employee) = employees += (employee.employeeId -> employee)

  def deleteEmployee(employeeId: Long) = employees -= employeeId
}