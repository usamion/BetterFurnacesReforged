package wily.betterfurnaces.container;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import wily.betterfurnaces.blockentity.BlockEntitySmeltingBase;
import wily.betterfurnaces.items.ItemEnergyFuel;
import wily.betterfurnaces.items.ItemLiquidFuel;

public class SlotHeater extends Slot {

    private BlockEntitySmeltingBase tf;

    public SlotHeater(Container te, int slotIndex, int xPosition, int yPosition) {
        super(te, slotIndex, xPosition, yPosition);
            this.tf = (BlockEntitySmeltingBase) te;
    }

    /**
     * Check if the stack is allowed to be placed in this slot, used for armor slots as well as furnace fuel.
     */
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof ItemEnergyFuel || stack.getItem() instanceof ItemLiquidFuel && !tf.hasXPTank();
    }


    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void setChanged() {
            tf.onUpdateSent();
    }

}