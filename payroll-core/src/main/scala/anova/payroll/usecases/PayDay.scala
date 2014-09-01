package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import anova.payroll.usecases.Entities._


import org.joda.time.DateTime

class PayDay(implicit val employeeGateway: EmployeeGateway) {

  def execute(date: DateTime) {
    employeeGateway.getEmployees.foreach {
      employee =>
        if (employee.isPayDay)
          employeeGateway.addPaycheck(employee.employeeId, employee.pay(date))
    }
  }
}
