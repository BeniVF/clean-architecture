package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.Entities._


import org.joda.time.DateTime
import anova.payroll.usecases.Data.EmployeeData

class PayDay(implicit val toEmployee: EmployeeData => Employee, val employeeGateway: EmployeeGateway) {

  def execute(date: DateTime) {
    employeeGateway.getEmployees.foreach {
      employee =>
        if (employee.isPayDay)
          employee.pay(date)
    }
  }
}
