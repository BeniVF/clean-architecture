package anova.payroll.usecases

import anova.payroll.modules.{MemoryPayrollGatewayModule, PayrollModule}
import org.scalatest.{Matchers, FlatSpec}
import anova.payroll.usecases.Data.EmployeeData
import anova.payroll.usecases.Entities.Employee

class BaseEmployeeSpec extends FlatSpec with Matchers with PayrollModule with MemoryPayrollGatewayModule {
  import anova.payroll.gateway.EntityFactory._
  implicit override def toEmployee(employeeData: EmployeeData): Employee = toEmployeeProxy(employeeData)

}
