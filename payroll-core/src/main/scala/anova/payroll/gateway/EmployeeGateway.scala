package anova.payroll.gateway

import anova.payroll.usecases.Data._
import org.joda.time.DateTime

import scala.collection.concurrent.TrieMap

trait EmployeeGateway {
  def getEmployee(employeeId: Long): Option[EmployeeData]

  def getEmployees : List[EmployeeData]

  def saveEmployee(employee: EmployeeData)

  def deleteEmployee(employeeId: Long)

  def addTimeCard(employeeId: Long, timeCard: TimeCardData)

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCardData]

  def addSalesReceipt(employeeId: Long, salesReceipt: SalesReceiptData)

  def getSalesReceipt(employeeId: Long, date: DateTime): Option[SalesReceiptData]

  def addPaycheck(employeeId: Long, paycheck: PaycheckData)

  def getPaycheck(employeeId: Long, date: DateTime): Option[PaycheckData]

}

object EntityFactory {
  import anova.payroll.usecases.Entities._
  import anova.payroll.usecases.Data._
  def toEmployeeProxy(data: EmployeeData)(implicit employeeGateway: EmployeeGateway) : Employee = EmployeeProxy(EmployeeImplementation(data))

  case class EmployeeProxy(employee: Employee)(implicit employeeGateway: EmployeeGateway) extends Employee {
    def id: Long = employee.id

    def isPayDay: Boolean = employee.isPayDay

    def pay(date: DateTime): PaycheckData = {
      val paycheck = employee.pay(date)
      employeeGateway.addPaycheck(id, paycheck)
      paycheck
    }
  }
}


class MemoryEmployeeGateway(implicit initialEmployees: List[EmployeeData]) extends EmployeeGateway {
  protected lazy val employees = toTrieMap(initialEmployees)
  protected lazy val timeCards = TrieMap[Long, Map[DateTime, TimeCardData]]()
  protected lazy val salesReceipts = TrieMap[Long, Map[DateTime, SalesReceiptData]]()
  protected lazy val paychecks = TrieMap[Long, Map[DateTime, PaycheckData]]()

  private def toTrieMap(templates: List[EmployeeData]): TrieMap[Long, EmployeeData] = {
    val ids = templates.map(_.employeeId)
    val result = TrieMap[Long, EmployeeData]()
    (ids zip templates).toSeq.foreach(result += _)
    result
  }

  def getEmployees : List[EmployeeData] = employees.values.toList

  def addTimeCard(employeeId: Long, timeCard: TimeCardData) {
    val employeeTimeCards = timeCards.getOrElse(employeeId, Map[DateTime, TimeCardData]())
    timeCards += (employeeId -> (employeeTimeCards + (timeCard.date -> timeCard)))
  }

  def addSalesReceipt(employeeId: Long, salesReceipt: SalesReceiptData) = {
    val employeeSalesReceipts = salesReceipts.getOrElse(employeeId, Map[DateTime, SalesReceiptData]())
    salesReceipts += (employeeId -> (employeeSalesReceipts + (salesReceipt.date -> salesReceipt)))
  }

  def addPaycheck(employeeId: Long, paycheck: PaycheckData) = {
    val employeePaychecks = paychecks.getOrElse(employeeId, Map[DateTime, PaycheckData]())
    paychecks += (employeeId -> (employeePaychecks + (paycheck.payPeriodEndDate -> paycheck)))
  }

  def getTimeCard(employeeId: Long, date: DateTime): Option[TimeCardData] =
    timeCards.get(employeeId).map(timeCards => timeCards.get(date)).flatten

  def getSalesReceipt(employeeId: Long, date: DateTime): Option[SalesReceiptData] =
    salesReceipts.get(employeeId).map(salesReceipts => salesReceipts.get(date)).flatten

  def getPaycheck(employeeId: Long, date: DateTime) =  paychecks.get(employeeId).map(paychecks => paychecks.get(date)).flatten

  def getEmployee(employeeId: Long): Option[EmployeeData] = employees.get(employeeId)

  def saveEmployee(employee: EmployeeData) = employees += (employee.employeeId -> employee)

  def deleteEmployee(employeeId: Long) = employees -= employeeId


}