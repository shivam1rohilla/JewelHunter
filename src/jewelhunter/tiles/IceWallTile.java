package jewelhunter.tiles;

import jewelhunter.gfx.Assets;

public class IceWallTile extends Tile {

	public IceWallTile( int id) {
		super(Assets.iceWall, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid(){
		return true;
	}
	
}
