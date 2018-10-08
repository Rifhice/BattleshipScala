package battleship.model

/** A position is defined by an x and a y and has a boolean isHit defining whether the position was shot.
 *
 *  @constructor create a new position with an x, a y and a boolean.
 *  @param x x
 *  @param y y
 *  @param isHit is false by default
 */
case class Position(x :Int, y :Int, var isHit: Boolean = false)
