package anova.payroll.usecases

import anova.payroll.usecases.AddSalariedEmployee.AddEmployeeRequest
import com.github.nscala_time.time.StringImplicits
import org.joda.time.DateTime

class PaydaySpec extends BaseEmployeeSpec with StringImplicits {


  it should "pay single salaried employee" in {
    val request = AddEmployeeRequest(1, "Joe", "Joe's Home", salary = Option(1100.00))
    addSalariedEmployee.execute(request)

    val payDate = "30-11-20".toDateTime
    payDay.execute(payDate)

    validatePaycheck(1, payDate, 1100.00)
  }

  it should "pay multiple salaried employee" in {
    addSalariedEmployee.execute(AddEmployeeRequest(1, "Joe", "Joe's Home", salary = Option(1100.00)))
    addSalariedEmployee.execute(AddEmployeeRequest(2, "Joe", "Joe's Home", salary = Option(1000.00)))
    addSalariedEmployee.execute(AddEmployeeRequest(3, "Joe", "Joe's Home", salary = Option(1200.00)))

    val payDate = "30-11-20".toDateTime
    payDay.execute(payDate)

    validatePaycheck(1, payDate, 1100.00)
    validatePaycheck(2, payDate, 1000.00)
    validatePaycheck(3, payDate, 1200.00)
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
