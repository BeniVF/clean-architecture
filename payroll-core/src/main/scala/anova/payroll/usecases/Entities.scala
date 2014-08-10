package anova.payroll.usecases

object Entities {
  sealed trait Schedule
  case object MonthlySchedule extends Schedule
  case object BiweeklySchedule extends Schedule
  case object WeeklySchedule extends Schedule

  sealed trait Method
  case object HoldMethod extends Method

  sealed trait Classification
  case class SalariedClassification(salary: BigDecimal) extends Classification
  case class CommissionedClassification(salary: BigDecimal, commissionRate: BigDecimal) extends Classification
  case class HourlyClassification(hourlyRate: BigDecimal) extends Classification

  case class EmployeeStatus(classification: Classification, schedule: Schedule, method: Method = HoldMethod)

  case class Employee(employeeId: Long, name: String, status: EmployeeStatus)

}
