package anova.payroll.usecases

import org.joda.time.DateTime


object Entities {

  import Data._

  case class EmployeeEntity(data: Employee) {
    def isPayDay: Boolean = true

    def salary: BigDecimal = data.employeePayment.classification match {
      case SalariedClassification(salary) => salary
    }

    def pay(date: DateTime): Paycheck =
      Paycheck(date, salary, Map("Disposition" -> "Hold"), 0.0, salary)
  }

  trait PaymentClassificationEntity {
    def calculateSalary: BigDecimal
  }

  implicit def toEmployee(data: Employee) = EmployeeEntity(data)

}

object Data {

  sealed trait PaymentSchedule

  case object MonthlySchedule extends PaymentSchedule

  case object BiweeklySchedule extends PaymentSchedule

  case object WeeklySchedule extends PaymentSchedule

  sealed trait PaymentMethod

  case object HoldMethod extends PaymentMethod

  sealed trait PaymentClassification

  case class SalariedClassification(salary: BigDecimal) extends PaymentClassification

  case class CommissionedClassification(salary: BigDecimal, commissionRate: BigDecimal) extends PaymentClassification

  case class HourlyClassification(hourlyRate: BigDecimal) extends PaymentClassification

  case class TimeCard(date: DateTime, hours: BigDecimal)

  case class SalesReceipt(date: DateTime, amount: BigDecimal)

  case class Paycheck(payPeriodEndDate: DateTime, grossPay: BigDecimal, fields: Map[String, String], deductions: BigDecimal, netPay: BigDecimal)

  case class EmployeePayment(classification: PaymentClassification, schedule: PaymentSchedule, method: PaymentMethod = HoldMethod)

  case class Employee(employeeId: Long, name: String, employeePayment: EmployeePayment)

}

object EntitiesBuilder {

  import Data._

  object EmployeeBuilder {
    def apply(employeeId: Long, name: String, employeePayment: EmployeePayment = EmployeePaymentBuilder()) =
      Employee(employeeId, name, employeePayment)
  }

  object EmployeePaymentBuilder {
    def apply(classification: PaymentClassification = SalariedClassification(1000.00), schedule: PaymentSchedule = MonthlySchedule, method: PaymentMethod = HoldMethod) = {
      EmployeePayment(classification, schedule, method)
    }
  }

  object TimeCardBuilder {
    def apply(date: DateTime, hours: BigDecimal) = TimeCard(date, hours)
  }

  object SalesReceiptBuilder {
    def apply(date: DateTime, amount: BigDecimal) = SalesReceipt(date, amount)
  }

}