// Liam Major 30223023
package stubbing;

import powerutility.PowerGrid;

public class StubbedGrid {
	public static PowerGrid instance() {
		PowerGrid.engageUninterruptiblePowerSource();
		return PowerGrid.instance();
	}
}
