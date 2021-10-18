package jewelhunter.tiles;

import jewelhunter.gfx.Animation;
import jewelhunter.gfx.Assets;

public class RockTile extends Tile {

	public RockTile( int id) {
		super(new Animation(10000, Assets.stone), id);
	}
	@Override
	public boolean isSolid(){
		return true;
	}
}
