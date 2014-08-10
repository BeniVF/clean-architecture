package anova.payroll.usecases

object Entities {
  sealed trait Schedule
  case object MonthlySchedule extends Schedule
  case object BiweeklySchedule extends Schedule

  sealed trait Method
  case object HoldMethod extends Method

  sealed trait Classification
  case class SalariedClassification(salary: BigDecimal, schedule: Schedule = MonthlySchedule, method: Method = HoldMethod) extends Classification
  case class CommissionedClassification(salary: BigDecimal, commissionRate: BigDecimal, schedule: Schedule = BiweeklySchedule) extends Classification

  case class Employee(employeeId: Long, name: String, classification: Classification)

}
