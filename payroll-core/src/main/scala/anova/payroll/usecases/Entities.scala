package anova.payroll.usecases

import org.joda.time.DateTime

object Entities {

  import Data._

  trait Employee {
    def id: Long
    def isPayDay: Boolean
    def pay(date: DateTime): PaycheckData
  }

  case class EmployeeImplementation(data: EmployeeData) extends Employee {

    def id: Long = data.employeeId

    def isPayDay: Boolean = true

    def salary: BigDecimal = data.classification match {
      case SalariedClassificationData(salary) => salary
      case _ => 0
    }

    def pay(date: DateTime): PaycheckData =
      PaycheckData(date, salary, Map("Disposition" -> "Hold"), 0.0, salary)
  }
}

object EntityFactory {
  import anova.payroll.usecases.Entities._
  import Data._
  implicit def toEmployee(data: EmployeeData) : Employee = EmployeeImplementation(data)
}



object Data {

  sealed trait PaymentScheduleData

  case object MonthlyScheduleData extends PaymentScheduleData

  case object BiweeklyScheduleData extends PaymentScheduleData

  case object WeeklyScheduleData extends PaymentScheduleData

  sealed trait PaymentMethodData

  case object HoldMethodData extends PaymentMethodData

  sealed trait PaymentClassificationData

  case class SalariedClassificationData(salary: BigDecimal) extends PaymentClassificationData

  case class CommissionedClassificationData(salary: BigDecimal, commissionRate: BigDecimal) extends PaymentClassificationData

  case class HourlyClassificationData(hourlyRate: BigDecimal) extends PaymentClassificationData

  case class EmployeeData(employeeId: Long, name: String, classification: PaymentClassificationData, schedule: PaymentScheduleData, method: PaymentMethodData = HoldMethodData)

  case class TimeCardData(date: DateTime, hours: BigDecimal)

  case class SalesReceiptData(date: DateTime, amount: BigDecimal)

  case class PaycheckData(payPeriodEndDate: DateTime, grossPay: BigDecimal, fields: Map[String, String], deductions: BigDecimal, netPay: BigDecimal)

}

object DataBuilder {

  import Data._

  object EmployeeBuilder {
    def apply(employeeId: Long, name: String, classification: PaymentClassificationData= SalariedClassificationData(1000.00), schedule: PaymentScheduleData = MonthlyScheduleData, method: PaymentMethodData = HoldMethodData) =
      EmployeeData(employeeId, name, classification, schedule, method)
  }

  object TimeCardBuilder {
    def apply(date: DateTime, hours: BigDecimal) = TimeCardData(date, hours)
  }

  object SalesReceiptBuilder {
    def apply(date: DateTime, amount: BigDecimal) = SalesReceiptData(date, amount)
  }

}