package main.keyEvent;

public class GameKeyType {

	public enum Information {
		DROP_SHAPE(1), MOVE_SHAPE_LEFT(2), MOVE_SHAPE_RIGHT(3)
		, MOVE_SHAPE_DOWN(4), ROTATE_SHAPE(5), MOVE_SHAPE_DOWN_BY_TIME(6)
		, I(7), J(8), L(9), O(10), S(11), T(12), Z(13);
		
		private int value;
		
		private Information(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
	}
}
