package anova.payroll.usecases

import anova.payroll.modules.{MemoryPayrollGatewayModule, PayrollModule}
import org.scalatest.{Matchers, FlatSpec}

class BaseEmployeeSpec extends FlatSpec with Matchers with PayrollModule with MemoryPayrollGatewayModule {
}
