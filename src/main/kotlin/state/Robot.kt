package state


import arrow.Kind
import arrow.core.ForId
import arrow.core.Tuple2
import arrow.data.State
import arrow.data.StateTPartialOf
import arrow.data.fix
import arrow.data.run
import arrow.instances.monad
import java.util.*

data class Robot(val id: Long,
                 val sentient: Boolean,
                 val canWalk: Boolean,
                 val canDive: Boolean,
                 val canFly: Boolean,
                 val name: String ,
                 val model: String)

val rng = Random()

fun createRandomRobot():Robot {

    val id = rng.nextLong()
    val sentient = rng.nextBoolean()
    val canWalk = rng.nextBoolean()
    val canDive = rng.nextBoolean()
    val canFly = rng.nextBoolean()
    val name  = if  (rng.nextBoolean() ) "Terminator" else "Other"
    val model = if  (rng.nextBoolean() ) "T1000" else "T800"

    return Robot(id,canWalk,canDive,canFly,sentient,name,model)
}

// ------------------- //

fun createRobotV2():Robot {
    val id = rng.nextLong()
    val b = rng.nextBoolean()
    val canWalk = b
    val canDive = b
    val canFly = b
    val sentient = b
    val name  = if  (b) "Terminator" else "Other"
    val model = if  (b) "T1000" else "T800"

    return Robot(id,canWalk,canDive,canFly,sentient,name,model)
}

// ------------------------ //

class Seed(val long: Long)
{
    fun nextSeed():Seed = Seed(long * 6364136223846793005L + 1442695040888963407L)
}

fun nextBoolean(seed: Seed) =  Tuple2(seed.nextSeed(),seed.long > 0)
fun nextLong(seed: Seed) = Tuple2(seed.nextSeed(),seed.long)

fun createRobotV3(initialSeed: Long):Robot {

    val seed = Seed(initialSeed)
    val (seed1,id) = nextLong(seed)

    val (seed2,sentient) = nextBoolean(seed1)
    val (seed3,canWalk) = nextBoolean(seed2)
    val (seed4,canDive) = nextBoolean(seed3)
    val (seed5,canFly) = nextBoolean(seed4)
    val (seed6, b) = nextBoolean(seed5)
    val name  = if  (b ) "Terminator" else "Other"
    val (seed7, b1) = nextBoolean(seed6)
    val model = if  (b1) "T1000" else "T800"

    return Robot(id,canWalk,canDive,canFly,sentient,name,model)
}


fun nextBooleanWithState():State<Seed,Boolean> =
     State { seed -> nextBoolean(seed)}


fun nextLongWithState():State<Seed,Long> =
        State { seed -> nextLong(seed)  }



fun createRobotV4(long: Long) = innerCreateV4().fix().run(Seed(long)).b

private fun innerCreateV4() = State().monad<Seed>().binding {

    val id        = nextLongWithState()   .bind()
    val sentient  = nextBooleanWithState().bind()
    val canWalk   = nextBooleanWithState().bind()
    val canDive   = nextBooleanWithState().bind()
    val canFly    = nextBooleanWithState().bind()
    val name  = if (nextBooleanWithState().bind()) "Terminator" else "Other"
    val model = if (nextBooleanWithState().bind()) "T1000" else "T800"

    Robot(id,canWalk,canDive,canFly,sentient,name,model)
}


private fun innerCreateV5(): Kind<StateTPartialOf<ForId, Seed>, Robot> {
    val l = nextLongWithState()
    val b = nextBooleanWithState()

    return State().monad<Seed>().binding {
        val id = l.bind()
        val sentient = b.bind()
        val canWalk = b.bind()
        val canDive = b.bind()
        val canFly = b.bind()
        val name = if (b.bind()) "Terminator" else "Other"
        val model = if (b.bind()) "T1000" else "T800"
        val robot = Robot(id,canWalk,canDive,canFly,sentient,name,model)
        robot
    }
}

fun createRobotV5(long: Long) = innerCreateV5().fix().run(Seed(long)).b


fun main() {


    println(createRandomRobot())
    println(createRandomRobot())

    println(createRobotV3(30))
    println(createRobotV3(30))

    println(createRobotV4(30))
    println(createRobotV4(30))

}

