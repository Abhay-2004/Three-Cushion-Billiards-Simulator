package hw2;
/**
 * 
 */
import api.PlayerPosition;
import api.BallType;
import static api.PlayerPosition.*;
import static api.BallType.*;
/**
 * Class that models the game of three-cushion billiards.
 * @author Abhay
 *
 */

public class ThreeCushion {
	

	
	/**
	 * This variable stores the score for player A
	 */
	private int player_A_score;
	
	/**
	 * This variable store the score for player B
	 */
	private int player_B_score;
	
	/**
	 * This variable stores the total points needed for the player to win
	 */
	private int total_pts_to_win;
	
	/**
	 * This variable stores the inning count (Note: in 1 game, there can be several 
	 */
	private int inning_count;
	
	/**
	 * This variable is used to check if the white ball is currently being used
	 */
	private boolean white_Ball;
	
	/**
	 * This variable is used to check if inning A is for player A/playerB
	 */
	private boolean Inning_A;
	
	/**
	 * This variable is used for checking if some case gives you a foul
	 */
	private boolean check_foul_rules;
	
	/**
	 * this variable is to keep track of the breakshot
	 */
	private boolean breakshot;
	
	/**
	 * this variable is to check if the shot is valid or not. It checks for the required shots as per the instructions
	 */
	private boolean valid_shot;

	/**
	 * keeps track of the bankshot
	 */
	private boolean bankshot;
	
	/**
	 * This stores the boolean value for the shot when it's played/started
	 */
	private boolean Start_shot;
	
	/**
	 * This is used to keep track if of the innings, returns true or false depending on the condition
	 */
	private boolean innings_started;

	/**
	 * Sets the GameOver to be false in beginning, when it turns true, game ends
	 */
	private boolean GameOver;
	
	/**
	 * Store the value of the total times the ball hit's the cushion
	 */
	private int Cushion_impact_count;
	
	/**
	 * to store the final valid score and return it
	 */
	private int validCushion_score;
	
	/**
	 * to check if the player hits the red ball, mostly used while calling the foul
	 */
	private boolean Red_ball_hit_check;
	
	/**
	 * This is used to check if the the player hits the opponents ball also
	 */
	private boolean opponentball_hit;
	
	
	/**
	 * This is the constructor for the class, initializing of variables are done in this
	 * Creates a new game of three-cushion billiards with a given lag winner and the predetermined number of points required to win the game. 
	 * The inning count starts at 1.
	 * @param lagWinner
	 * @param pointToWin
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointToWin) {
		player_A_score = 0;
		player_B_score = 0;
		
		inning_count = 1;
		
		check_foul_rules = false;
		
		if (lagWinner == PLAYER_A) {
			Inning_A = true;
		} else {
			Inning_A = false;
		}
		
		innings_started = false;
		
		Start_shot = false;
		
		total_pts_to_win = pointToWin;
	}
	/**
	 * Sets whether the player that won the lag chooses to break (take first shot), or chooses the other player to break. 
	 * If this method is called more than once it should have no effect. 
	 * In other words, the lag winner can only choose these options once and may not change their mind afterwards.

	 * @param selfBreak
	 * @param cueBall
	 */

	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
		if (!check_foul_rules) {
			breakshot = true;
		
			if ((cueBall == WHITE)) {
				white_Ball = true;
			} else {
				white_Ball = false;
			}
		
			if (!selfBreak) {
				Inning_A = !Inning_A;
				white_Ball = !white_Ball;
			}
		}
		check_foul_rules = true;
		
	}
	/**
	 * Indicates the cue stick has struck the given ball. If a shot has not already begun, indicates the start of a new shot. If this method is called while a shot is still in progress (i.e., endShot() has not been called for the previous shot), the player has committed a foul (see the method foul()). Also, if the player strikes anything other than their own cue ball, they committed a foul.
		Calling this method signifies both the start of a shot and the start of an inning, assuming a shot or inning has not already begun, respectively.

		Even if a foul has been committed, calling this method is considered the start of a shot. That includes even the case when the player strikes a ball other than their own cue ball. It is expected that the endShot() method will be called in any case to indicate the end of the shot.

		No play can begin until the lag player has chosen who will break (see lagWinnderChooses). If this method is called before the break is chosen, it should do nothing.

		If this method is called after the game has ended, it should do nothing.
	 * @param ball
	 */
	
	public void cueStickStrike(BallType ball) {
	    if (GameOver) {
	        return;
	    }
	    
	    if (!innings_started) {
	        innings_started = true;
	    }
	    
	    if (Start_shot) {
	        foul();
	        check_foul_rules = true;
	    } else {
	        Start_shot = true;
	        if (ball == getCueBall()) {
	            Red_ball_hit_check = false;
	            opponentball_hit = false;
	            Cushion_impact_count = 0;
	            valid_shot = true;
	        } else {
	            foul();
	        }
	    }
	}
	
	/**
	 * Indicates the player's cue ball has struck the given ball.
		A ball strike cannot happen before a stick strike.
		 If this method is called before the start of a shot (i.e., cueStickStrike() is called), it should do nothing.

		If this method is called after the game has ended, it should do nothing.
	 * @param ball
	 */


	public void cueBallStrike(BallType ball) {
		if(GameOver == true) {
			return;
		}
		else if (GameOver == false) {
			if (Cushion_impact_count < 3) {
				valid_shot = false;
			}
		
			if (ball == RED) {
				Red_ball_hit_check = true;
			} else {
				opponentball_hit = true;
				if (breakshot == true) {
					if(Red_ball_hit_check == false) {
						foul();
					}
					
				}
			}
			validCushion_score = Cushion_impact_count;
		}
	}
	

/**
 * Indicates the given ball has impacted the given cushion.
A cushion impact cannot happen before a stick strike. 
If this method is called before the start of a shot (i.e., cueStickStrike() is called), it should do nothing.

If this method is called after the game has ended, it should do nothing.
 */
	public void cueBallImpactCushion() {
	    if (GameOver) {
	        return;
	    }

	    if (breakshot) {
	        if (!Red_ball_hit_check) {
	            foul();
	        }
	    }

	    Cushion_impact_count += 1;
	}
/**
 * Indicates that all balls have stopped motion. If the shot was valid and no foul was committed, the player scores 1 point.
The shot cannot end before it has started with a call to cueStickStrike. If this method is called before cueStickStrike, it should be ignored.

A shot cannot end before the start of a shot. If this method is called before the start of a shot (i.e., cueStickStrike() is called), it should do nothing.

If this method is called after the game has ended, it should do nothing.
 */

	public void endShot() {
		Start_shot = false;
		breakshot = false;
		bankshot = false;

		if (innings_started) {
			if ((Red_ball_hit_check)) {
				if(opponentball_hit) {
					if(validCushion_score>=3) {
						if (Inning_A) {
							player_A_score += 1;
						} else {
							player_B_score += 1;
						}
						if (valid_shot) {
							bankshot = true;
						}
	
						if(total_pts_to_win<=player_A_score ||total_pts_to_win<=player_B_score) {
							innings_started = false;
							GameOver = true;
						}
					}
			}
			}
			else {
				innings_started = false;
				inning_count += 1;
				if (Inning_A == true) {
					  Inning_A = false;
					} else {
					  Inning_A = true;
					}

				if (white_Ball) {
				    white_Ball = false;
				} else {
				    white_Ball = true;
				}
			}
		}
		
	}
	
/**
 * A foul immediately ends the player's inning, even if the current shot has not yet ended. When a foul is called, the player does not score a point for the shot.
A foul may also be called before the inning has started. In that case the player whose turn it was to shot has their inning forfeited and the inning count is increased by one.

No foul can be called until the lag player has chosen who will break (see lagWinnerChooses()). If this method is called before the break is chosen, it should do nothing.

If this method is called after the game has ended, it should do nothing.


 */
	public void foul() {
		if (!GameOver){
			if(check_foul_rules == true) {
		
			check_foul_rules = false;
			innings_started = false;
			inning_count += 1;
			if (Inning_A == true) {
				  Inning_A = false;
				} else {
				  Inning_A = true;
				}

			if (white_Ball) {
			    white_Ball = false;
			} else {
			    white_Ball = true;
			}
		}
	}
	}
	
	/**
	 * Gets the number of points scored by Player A.
	 * @return player_A_score
	 */
	public int getPlayerAScore() {
		return player_A_score;
	}
	
	/**
	 * Gets the number of points scored by Player B.
	 * @return player_B_score
	 */
	public int getPlayerBScore() {
		return player_B_score;
	}
	
	/**
	 * Gets the inning number. 
	 * The inning count starts at 1.
	 * @return inning_count
	 */
	public int getInning() {
		return inning_count;
	}
	/**
	 * This method is used to change the players
	 * @param player
	 * @return Player_A or Player_B
	 */
	private static PlayerPosition other_player(PlayerPosition player) {
		if(player == PLAYER_A) {
			return PLAYER_B;
		}
		else {
			return PLAYER_A;
		}
		
	}
	/**
	 * This method is to change the cue ball color
	 * @param which_ball
	 * @return Yellow or white
	 */
	private static BallType othercueball(BallType which_ball) {
		if(which_ball ==WHITE) {
			return YELLOW;
		}
		else {
			return WHITE;
		}
	}
	
	/**
	 * Gets the cue ball of the current player. 
	 * If this method is called in between innings, the cue ball should be the for the player of the upcoming inning.
	 * If this method is called before the lag winner has chosen a cue ball, the cue ball is undefined (this method may return anything).
	 * @return Color_of_the_ball
	 */
	public BallType getCueBall() {
		BallType Color_of_the_ball;
		if(white_Ball) {
			Color_of_the_ball = WHITE;
		} else {
			Color_of_the_ball = YELLOW;
		}
		return Color_of_the_ball;
	}
	/**
	 * Gets the current player.
	 * If this method is called in between innings, the current player is the player of the upcoming inning. 
	 * If this method is called before the lag winner has chosen to break, the current player is undefined (this method may return anything).
	 * @return Current_person
	 */
	

	public PlayerPosition getInningPlayer() {
		PlayerPosition Current_person;
		if (Inning_A) {
			Current_person = PLAYER_A;
		} else {
			Current_person = PLAYER_B;
		}
		return Current_person;
	}
	
	/**
	 * Returns true if and only if this is the break shot (i.e., the first shot of the game).
	 * @return breakshot
	 */
	public boolean isBreakShot() {
		return breakshot;
	}
	
	/**
	 * Returns true if and only if the most recently completed shot was a bank shot. 
	 * A bank shot is when the cue ball impacts the cushions at least 3 times and then strikes both object balls.
	 * @return bankshot
	 */
	public boolean isBankShot() {
		return bankshot;
	}
	
	/**
	 * Returns true if a shot has been taken (see cueStickStrike()), but not ended (see endShot()).
	 * @return Start_shot
	 */
	public boolean isShotStarted() {
		return Start_shot;
	}
	
	/**
	 * Returns true if the shooting player has taken their first shot of the inning.
	 * The inning starts at the beginning of the shot.
	 * @return innings_started
	 */
	public boolean isInningStarted() {
		return innings_started;
	}
	
	/**
	 * Returns true if the game is over (i.e., one of the players has reached the designated number of points to win).
	 * @return GameOver
	 */

	public boolean isGameOver() {
		return GameOver;
	}
	
	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}
}
