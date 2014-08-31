package anova.payroll.usecases

import anova.payroll.usecases.AddSalariedEmployee.AddEmployeeRequest
import com.github.nscala_time.time.StringImplicits
import org.joda.time.DateTime

class PaydaySpec extends BaseEmployeeSpec with StringImplicits {
  it should "pay single salaried employee" in {
    val employeeId = 1
    val pay = 1100.00
    val request = AddEmployeeRequest(employeeId, "Joe", "Joe's Home", salary = Option(pay))
    addSalariedEmployee.execute(request)
    val payDate = "30-11-20".toDateTime
    payDay.execute(payDate)

    validatePaycheck(employeeId, payDate, pay)
  }

  def validatePaycheck(employeeId: Long, payDate: DateTime, pay: BigDecimal) {
    val paycheck = employeeGateway.getPaycheck(employeeId, payDate).get
    paycheck.payPeriodEndDate shouldBe payDate
    pay shouldBe paycheck.grossPay
    "Hold" shouldBe paycheck.fields("Disposition")
    0.0 shouldBe paycheck.deductions
    pay shouldBe paycheck.netPay
  }
}
