package jewelhunter.tiles;

import jewelhunter.gfx.Assets;

public class SteelWallTile extends Tile {

	public SteelWallTile( int id) {
		super(Assets.steelWall, id);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean isSolid(){
		return true;
	}
}
