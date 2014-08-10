package anova.payroll.usecases

object Entities {
  sealed trait Classification

  case class Employee(employeeId: Long, name: String)

}
