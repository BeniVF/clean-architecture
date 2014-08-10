package anova.payroll.usecases

object Entities {
  sealed trait Classification
  case class SalariedClassification(salary: BigDecimal) extends Classification


  case class Employee(employeeId: Long, name: String, classification: Classification)

}
