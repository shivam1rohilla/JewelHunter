package jewelhunter.tiles;

import jewelhunter.gfx.Assets;

public class OutTile extends Tile {

	public OutTile( int id) {
		super(Assets.signExit, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isExit() {
		return true;
	}
	
}
