package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.Entities.{Paycheck, EmployeePayment, Employee, SalariedClassification}


import org.joda.time.DateTime

class PayDay(implicit val employeeGateway: EmployeeGateway) {

  def execute(date: DateTime) {
    employeeGateway.getEmployees.foreach {
      case Employee(employeeId, _, EmployeePayment(SalariedClassification(salary), _, _)) =>
        employeeGateway.addPaycheck(employeeId, Paycheck(date, salary, Map("Disposition" -> "Hold"), 0.0, salary))
    }
  }
}
