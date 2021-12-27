package wily.betterfurnaces.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import wily.betterfurnaces.BetterFurnacesReforged;
import wily.betterfurnaces.init.Registration;

import java.util.List;

public class ItemFuelEfficiency extends Item {

    public ItemFuelEfficiency(Properties properties) {
        super(properties);
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade_right_click").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GOLD).withItalic(true)));
        tooltip.add(new TranslatableComponent("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade.fuel").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
        if (stack.getItem() != Registration.FUEL.get())
            tooltip.add(new TranslatableComponent("tooltip." + BetterFurnacesReforged.MOD_ID + ".upgrade.unbreakable").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
    }
    public void inventoryTick(ItemStack stack, Level world, Entity player, int slot, boolean selected) {
        super.inventoryTick(stack, world, player, slot, selected);
        if (stack.isDamageableItem() && stack.getDamageValue() >= 256){
            stack.shrink(1);
        }
    }
}