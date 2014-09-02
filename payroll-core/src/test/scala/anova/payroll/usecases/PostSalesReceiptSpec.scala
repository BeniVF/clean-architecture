package anova.payroll.usecases

import anova.payroll.usecases.Data._
import anova.payroll.usecases.DataBuilder._
import anova.payroll.usecases.PostSalesReceipt.PostSalesReceiptRequest
import com.github.nscala_time.time.StringImplicits

class PostSalesReceiptSpec extends BaseEmployeeSpec with StringImplicits {

  override lazy val initialEmployees = List(EmployeeBuilder(1, "James"),
    EmployeeBuilder(2, "Arthur", CommissionedClassificationData(1000.3, 10.0), BiweeklyScheduleData))


  it should "posts sales receipt" in {
    val employeeId = 2
    val date = "2012-11-20".toDateTime
    val amount = 2000.34
    postSalesReceipt.execute(PostSalesReceiptRequest(employeeId, date, amount))

    val salesReceipt = employeeGateway.getSalesReceipt(employeeId, date)

    salesReceipt shouldBe Option(SalesReceiptBuilder(date, amount))
  }

}
