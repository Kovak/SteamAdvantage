package cyano.steamadvantage.machines;

import cyano.poweradvantage.api.ConduitType;
import cyano.poweradvantage.api.PoweredEntity;
import cyano.poweradvantage.conduitnetwork.ConduitRegistry;
import cyano.poweradvantage.init.Fluids;
import cyano.steamadvantage.init.Power;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;



public class ElectricBoilerBlock  extends cyano.poweradvantage.api.simple.BlockSimplePowerMachine{

	
	public ElectricBoilerBlock() {
		super(Material.PISTON, 0.75f, Power.steam_power, ElectricBoilerTileEntity.ELECTRIC_POWER, Fluids.fluidConduit_general);
	}

	@Override
	public PoweredEntity createNewTileEntity(World world, int metaDataValue) {
		return new ElectricBoilerTileEntity();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState bs) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState bs, World world, BlockPos coord) {
		if(world.getTileEntity(coord) instanceof ElectricBoilerTileEntity){
			return ((ElectricBoilerTileEntity)world.getTileEntity(coord)).getComparatorOutput();
		}
		return 0;
	}

	@Override
	public boolean isPowerSink(ConduitType type){
		return !ConduitType.areSameType(Power.steam_power, type);
	}

	@Override
	public boolean isPowerSource(ConduitType type){
		return ConduitType.areSameType(Power.steam_power, type);
	}


	///// Overrides to make this a multi-type block /////
	
	/**
	 * This method is called whenever the block is placed into the world
	 */
	@Override
	public void onBlockAdded(World w, BlockPos coord, IBlockState state){
		super.onBlockAdded(w, coord, state);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, ElectricBoilerTileEntity.ELECTRIC_POWER);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, cyano.poweradvantage.init.Fluids.fluidConduit_general);
	}
	
	/**
	 * This method is called when the block is removed from the world by an entity.
	 */
	@Override
	public void onBlockDestroyedByPlayer(World w, BlockPos coord, IBlockState state){
		super.onBlockDestroyedByPlayer(w, coord, state);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, ElectricBoilerTileEntity.ELECTRIC_POWER);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, cyano.poweradvantage.init.Fluids.fluidConduit_general);
	}
	/**
	 * This method is called when the block is destroyed by an explosion.
	 */
	@Override
	public void onBlockDestroyedByExplosion(World w, BlockPos coord, Explosion boom){
		super.onBlockDestroyedByExplosion(w, coord, boom);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, ElectricBoilerTileEntity.ELECTRIC_POWER);
		ConduitRegistry.getInstance().conduitBlockPlacedEvent(w, w.provider.getDimension(), coord, cyano.poweradvantage.init.Fluids.fluidConduit_general);
	}

	
	///// end multi-type overrides /////
}