package anova.payroll.usecases

import anova.payroll.gateway.EmployeeGateway

class DeleteEmployee(implicit val employeeGateway: EmployeeGateway) {
  def execute(employeeId: Long) =
    employeeGateway.deleteEmployee(employeeId)


}
