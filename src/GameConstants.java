
public class GameConstants {
    /* DO NOT INSTANTIATE */
    private GameConstants() {}
    
    
    /* Observer Arguments (OA) */
    public static final String CURRENT_PLAYER_OA = "currentPlayer",
            UPDATE_OA = "update",
            END_TURN_ACTIVE_OA = "endTurnActive",
            MOVES_REMAINING_OA = "movesRemaining",
            BATTLE_WINNER_OA = "battleWinner",
            GAME_WINNER_OA = "gameWinner",
            GAME_RESET_OA = "gameReset";
    
    
    /* Main Instantiation Arguments (MIA) */
    public static final int ABORT_GAME_MIA = -999,
            HUMAN_PLAYER_MIA = 0,
            AI_PLAYER_MIA = 1;
}
