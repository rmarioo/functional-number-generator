

import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import state.*

class RobotTests {

    @Test
    fun `step 1 different robots returned  when called two times`() {
        val robot1 = createRandomRobot()
        val robot2 = createRandomRobot()

        assertThat(robot1, `is`(not(robot2)))
    }

    @Test
    fun `step 2 refactoring fail for v2 different robots returned`() {
        val robot1 = createRandomRobot()
        val robot2 = createRobotV2()

        assertThat(robot1, `is`(robot2))
    }

    @Test
    fun `step 3 v3 returns same robot when called multiple time with same seed`() {
        val robot1 = createRobotV3(42)
        val robot2 = createRobotV3(42)

        assertThat(robot1, `is`(robot2))
        assertThat(robot1, `is`(
                Robot(id = 42,
                        sentient = true,
                        canWalk = true,
                        canDive = false,
                        canFly = false,
                        name = "Other",
                        model = "T1000")))
    }


    @Test
    fun `step 4 v4 returns same robot when called multiple time with same seed`() {
        val robot1 = createRobotV4(42)
        val robot2 = createRobotV4(42)

        assertThat(robot1, `is`(robot2))
        assertThat(robot1, `is`(
                Robot(id = 42,
                        sentient = true,
                        canWalk = true,
                        canDive = false,
                        canFly = false,
                        name = "Other",
                        model = "T1000")))
    }
    @Test
    fun `step 5 v4 and v5 returns same robot when called multiple time with same seed`() {
        val robot1 = createRobotV4(42)
        val robot2 = createRobotV5(42)

        println(robot1)
        println(robot2)
        assertThat(robot1, `is`(robot2))
        assertThat(robot1, `is`(
                Robot(id = 42,
                      sentient = true,
                      canWalk = true,
                      canDive = false,
                      canFly = false,
                      name = "Other",
                      model = "T1000")))
    }





}
