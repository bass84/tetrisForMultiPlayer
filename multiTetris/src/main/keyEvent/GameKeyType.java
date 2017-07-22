package main.keyEvent;

public class GameKeyType {

	public enum GameKey {
		DROP_SHAPE(1), MOVE_SHAPE_LEFT(2), MOVE_SHAPE_RIGHT(3)
		, MOVE_SHAPE_DOWN(4), ROTATE_SHAPE(5), MOVE_SHAPE_DOWN_BY_TIME(6);
		
		private int value;
		
		private GameKey(int value) {
			this.value = value;
		}
		
		public int getName() {
			return value;
		}
		
	}
}
