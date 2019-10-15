package testing.helpers;

public enum TestResult {
	
	ANOTHER_SOURCE_INVALID("Player will be asked for another source coordinate because the previous move was invalid."),
	ANOTHER_DESTINATION_INVALID("Player will be asked for another destination coordinate because the previous move was invalid."),
	ANOTHER_MOVE_JUMP_POSSIBILITY("Player will be asked for another destination coordinate (previous move was a jump move) (there are still possibilities for a jump move)."),
	MOVE_END_NO_MORE_JUMP_POSSIBILITY("Player will NOT be asked for another destination coordinate (previous move was a jump move) because there are no more possibilities for a jump move."),
	MOVE_END_CROWNED("Player will NOT be asked for another destination coordinate (previous move was a jump move) because the piece has become king."),
	GAME_END_AS_DRAW("Game ended as a draw."),
	GAME_END_AS_WIN("Game ended as a win.");
	
	private String message;
	
	TestResult(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}
