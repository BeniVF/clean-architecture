package anova.payroll.usecases

import anova.payroll.usecases.Entities.{HourlyClassification, WeeklySchedule, EmployeePaymentBuilder, EmployeeBuilder}
import anova.payroll.usecases.PostTimeCardsToEmployee.PostTimeCardsToEmployeeRequest
import java.util.Date


class PostTimeCardsToEmployeeSpec extends BaseEmployeeSpec {

  override lazy val initialEmployees = List(EmployeeBuilder(1, "James"),
    EmployeeBuilder(2, "Arthur", EmployeePaymentBuilder(HourlyClassification(12.33), WeeklySchedule)))


  it should "posts time cards to employees" in {
    val employeeId = 2
    //Date(10,31,2001)
    val date = new Date
    postTimeCardsToEmployee.execute(PostTimeCardsToEmployeeRequest(date, 8.0, employeeId))

    val timeCard = employeeGateway.getTimeCard(employeeId, date)

    timeCard should not be None
    timeCard.get.hours shouldBe 8.0

  }

}
