package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway
import org.joda.time.DateTime

class PayDay(implicit val employeeGateway: EmployeeGateway) {
  import anova.payroll.usecases.EntityFactory._
  def execute(date: DateTime) {
    employeeGateway.getEmployees.foreach {
      employee =>
        if (employee.isPayDay)
          employeeGateway.addPaycheck(employee.id, employee.pay(date))
    }
  }
}
