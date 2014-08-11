package anova.payroll.usecases

import java.util.Date

object Entities {
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

  case class TimeCard(date: Date, hours: BigDecimal)

  case class EmployeePayment(classification: PaymentClassification, schedule: PaymentSchedule, method: PaymentMethod = HoldMethod)
  case class Employee(employeeId: Long, name: String, employeePayment: EmployeePayment)

  object EmployeeBuilder {
    def apply(employeeId: Long, name: String, employeePayment: EmployeePayment = EmployeePaymentBuilder()) =
      Employee(employeeId, name, employeePayment)
  }

  object EmployeePaymentBuilder {
    def apply(classification: PaymentClassification = SalariedClassification(1000.00), schedule: PaymentSchedule = MonthlySchedule, method: PaymentMethod = HoldMethod) ={
      EmployeePayment(classification, schedule, method)
    }
  }

}
