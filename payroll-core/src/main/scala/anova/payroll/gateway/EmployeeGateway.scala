package anova.payroll.gateway

import anova.payroll.usecases.Data._
import org.joda.time.DateTime

import scala.collection.concurrent.TrieMap

trait EmployeeGateway {
  def getEmployee(employeeId: Long): Option[Employee]

  def getEmployees : List[Employee]

  def saveEmployee(employee: Employee)

  def deleteEmployee(employeeId: Long)

  def addTimeCard(employeeId: Long, timeCard: TimeCard)

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCard]

  def addSalesReceipt(employeeId: Long, salesReceipt: SalesReceipt)

  def getSalesReceipt(employeeId: Long, date: DateTime): Option[SalesReceipt]

  def addPaycheck(employeeId: Long, paycheck: Paycheck)

  def getPaycheck(employeeId: Long, date: DateTime): Option[Paycheck]

}

class MemoryEmployeeGateway(implicit initialEmployees: List[Employee]) extends EmployeeGateway {
  protected lazy val employees = toTrieMap(initialEmployees)
  protected lazy val timeCards = TrieMap[Long, Map[DateTime, TimeCard]]()
  protected lazy val salesReceipts = TrieMap[Long, Map[DateTime, SalesReceipt]]()
  protected lazy val paychecks = TrieMap[Long, Map[DateTime, Paycheck]]()

  private def toTrieMap(templates: List[Employee]): TrieMap[Long, Employee] = {
    val ids = templates.map(_.employeeId)
    val result = TrieMap[Long, Employee]()
    (ids zip templates).toSeq.foreach(result += _)
    result
  }

  def getEmployees : List[Employee] = employees.values.toList

  def addTimeCard(employeeId: Long, timeCard: TimeCard) {
    val employeeTimeCards = timeCards.getOrElse(employeeId, Map[DateTime, TimeCard]())
    timeCards += (employeeId -> (employeeTimeCards + (timeCard.date -> timeCard)))
  }

  def addSalesReceipt(employeeId: Long, salesReceipt: SalesReceipt) = {
    val employeeSalesReceipts = salesReceipts.getOrElse(employeeId, Map[DateTime, SalesReceipt]())
    salesReceipts += (employeeId -> (employeeSalesReceipts + (salesReceipt.date -> salesReceipt)))
  }

  def addPaycheck(employeeId: Long, paycheck: Paycheck) = {
    val employeePaychecks = paychecks.getOrElse(employeeId, Map[DateTime, Paycheck]())
    paychecks += (employeeId -> (employeePaychecks + (paycheck.payPeriodEndDate -> paycheck)))
  }

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCard] =
    timeCards.get(employeeId).map(timeCards => timeCards.get(date)).flatten

  def getSalesReceipt(employeeId: Long, date: DateTime): Option[SalesReceipt] =
    salesReceipts.get(employeeId).map(salesReceipts => salesReceipts.get(date)).flatten

  def getPaycheck(employeeId: Long, date: DateTime) =  paychecks.get(employeeId).map(paychecks => paychecks.get(date)).flatten

  def getEmployee(employeeId: Long): Option[Employee] = employees.get(employeeId)

  def saveEmployee(employee: Employee) = employees += (employee.employeeId -> employee)

  def deleteEmployee(employeeId: Long) = employees -= employeeId


}