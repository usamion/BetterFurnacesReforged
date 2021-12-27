package wily.ultimatefurnaces.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import wily.betterfurnaces.blocks.BlockForgeBase;
import wily.ultimatefurnaces.init.Registration;
import wily.ultimatefurnaces.blockentity.BlockDiamondForgeTile;

import javax.annotation.Nullable;

public class BlockDiamondForge extends BlockForgeBase {

    public static final String DIAMOND_FORGE = "diamond_forge";

    public BlockDiamondForge(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return new BlockDiamondForgeTile(p_153215_, p_153216_);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createFurnaceTicker(level, type, Registration.DIAMOND_FORGE_TILE.get());
    }
}