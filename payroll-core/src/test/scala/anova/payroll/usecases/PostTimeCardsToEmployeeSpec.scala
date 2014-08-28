package anova.payroll.usecases

import anova.payroll.usecases.Entities._
import com.github.nscala_time.time.StringImplicits
import anova.payroll.usecases.PostTimeCardsToEmployee.PostTimeCardsToEmployeeRequest
import anova.payroll.usecases.Entities.HourlyClassification


class PostTimeCardsToEmployeeSpec extends BaseEmployeeSpec with StringImplicits {

  override lazy val initialEmployees = List(EmployeeBuilder(1, "James"),
    EmployeeBuilder(2, "Arthur", EmployeePaymentBuilder(HourlyClassification(12.33), WeeklySchedule)))


  it should "posts time cards to employees" in {
    val employeeId = 2
    val date = "2012-08-08".toDateTime
    val hours = 8.0
    postTimeCardsToEmployee.execute(PostTimeCardsToEmployeeRequest(date, hours, employeeId))

    val timeCard = employeeGateway.getTimeCard(employeeId, date)

    timeCard shouldBe Option(TimeCard(date, hours))
  }

}
