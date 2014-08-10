package anova.payroll.usecases

object Entities {
  sealed trait Schedule
  case object MonthlySchedule extends Schedule

  sealed trait Classification
  case class SalariedClassification(salary: BigDecimal, schedule: Schedule = MonthlySchedule) extends Classification

  case class Employee(employeeId: Long, name: String, classification: Classification)

}
