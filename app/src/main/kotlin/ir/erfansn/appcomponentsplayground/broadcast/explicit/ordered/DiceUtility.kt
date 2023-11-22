package ir.erfansn.appcomponentsplayground.broadcast.explicit.ordered

import kotlin.random.Random

const val ACTION_DICE_ROLLING = "ir.erfansn.appcomponentsplayground.broadcast.action.DICE_ROLLING"

const val KEY_DICE_RESULT = "dice_result"

val randomDiceNumber get() = Random.nextInt(1, 7)
