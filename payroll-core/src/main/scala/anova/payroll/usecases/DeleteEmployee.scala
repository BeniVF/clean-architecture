package anova.payroll.usecases

class DeleteEmployee(implicit val employeeGateway: EmployeeGateway) {
  def execute(employeeId: Long) =
    employeeGateway.deleteEmployee(employeeId)


}
