package wily.betterfurnaces.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import wily.betterfurnaces.BetterFurnacesReforged;
import wily.betterfurnaces.container.CobblestoneGeneratorContainer;
import wily.betterfurnaces.init.Registration;
import wily.betterfurnaces.network.Messages;
import wily.betterfurnaces.network.PacketCobButton;
import wily.betterfurnaces.recipes.CobblestoneGeneratorRecipes;
import wily.betterfurnaces.tileentity.CobblestoneGeneratorTileEntity;
import wily.betterfurnaces.util.FluidRenderUtil;

import java.util.List;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public abstract class CobblestoneGeneratorItemScreen<T extends CobblestoneGeneratorContainer> extends AbstractInventoryItemScreen<T> {

    public ResourceLocation GUI = new ResourceLocation(BetterFurnacesReforged.MOD_ID + ":" + "textures/container/cobblestone_generator_gui.png");
    public static final ResourceLocation WIDGETS = new ResourceLocation(BetterFurnacesReforged.MOD_ID + ":" + "textures/container/widgets.png");
    PlayerInventory playerInv;
    ITextComponent name;


    private List<CobblestoneGeneratorRecipes> recipes(){
        Messages.INSTANCE.sendToServer(new PacketCobButton(this.getMenu().getPos(), true));
        return  CobblestoneGeneratorTileEntity.recipes == null ?  Objects.requireNonNull(getMenu().te.getLevel().getRecipeManager().getAllRecipesFor(Registration.COB_GENERATION_RECIPE)) : CobblestoneGeneratorTileEntity.recipes;
    }
    public CobblestoneGeneratorItemScreen(T t, PlayerInventory inv, ITextComponent name) {
        super(t, inv, name);
        playerInv = inv;
        this.name = name;
    }
    public static class AbstractCobblestoneGeneratorItemScreenDefinition extends CobblestoneGeneratorItemScreen<CobblestoneGeneratorContainer> {
        public AbstractCobblestoneGeneratorItemScreenDefinition(CobblestoneGeneratorContainer container, PlayerInventory inv, ITextComponent name) {
            super(container, inv, name);
        }
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrix);
        super.render(matrix, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrix, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
    }


    @Override
    protected void renderLabels(MatrixStack matrix, int mouseX, int mouseY) {
        ITextComponent invname = this.playerInv.getDisplayName();
        int actualMouseX = mouseX - ((this.width - this.getXSize()) / 2);
        int actualMouseY = mouseY - ((this.height - this.getYSize()) / 2);
        this.minecraft.font.draw(matrix, this.playerInv.getDisplayName(), 7, this.getYSize() - 93, 4210752);
        this.minecraft.font.draw(matrix, name, 7 + this.getXSize() / 2 - this.minecraft.font.width(name.getString()) / 2, 6, 4210752);
        addTooltips(matrix, actualMouseX, actualMouseY);
    }

    private void addTooltips(MatrixStack matrix, int mouseX, int mouseY) {
        if (mouseX >= 81 && mouseX <= 95 && mouseY >= 25 && mouseY <= 39) {
            this.renderTooltip(matrix, recipes().get(getMenu().te.resultType).getResultItem(), mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(MatrixStack matrix, float partialTicks, int mouseX, int mouseY) {
        double actualMouseX = mouseX - getGuiLeft();
        double actualMouseY = mouseY - getGuiTop();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);
        int relX = (this.width - this.getXSize()) / 2;
        int relY = (this.height - this.getYSize()) / 2;
        this.blit(matrix, relX, relY, 0, 0, this.getXSize(), this.getYSize());
        renderGuiItem(recipes().get(getMenu().te.resultType).getResultItem(),getGuiLeft() + 80, getGuiTop() + 24, 0.75F, 0.75F);
        this.minecraft.getTextureManager().bind(WIDGETS);
        if (actualMouseX>= 81 && actualMouseX <= 95 && actualMouseY >= 25 && actualMouseY <= 39){
            this.blit(matrix, getGuiLeft() + 81, getGuiTop() + 25, 98, 157, 14, 14);
        } else this.blit(matrix, getGuiLeft() + 81, getGuiTop() + 25, 84, 157, 14, 14);
        int i;
        i = ((CobblestoneGeneratorContainer) this.getMenu()).getCobTimeScaled(16);
        if (i > 0) {
            FluidStack lava = new FluidStack(Fluids.FLOWING_LAVA, 1000);
            FluidStack water = new FluidStack(Fluids.WATER, 1000);
            FluidRenderUtil.renderTiledFluid(matrix, this, 58, 44, 17, 12, lava, false);
            FluidRenderUtil.renderTiledFluid(matrix, this, 101, 44, 17, 12, water, true);
            Minecraft.getInstance().getTextureManager().bind(GUI);
            this.blit(matrix, getGuiLeft() + 58, getGuiTop() + 44, 176, 24, i + 1, 12);
            this.blit(matrix, getGuiLeft() + 117 - i, getGuiTop() + 44, 192 - i, 36, 17, 12);

        }
        Minecraft.getInstance().getTextureManager().bind(GUI);
        this.blit(matrix, getGuiLeft() + 58, getGuiTop() + 44, 176, 0, 17, 12);
        this.blit(matrix, getGuiLeft() + 101, getGuiTop() + 44, 176, 12, 17, 12);

    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        double actualMouseX = mouseX - getGuiLeft();
        double actualMouseY = mouseY - getGuiTop();
        if (actualMouseX >= 81 && actualMouseX <= 95 && actualMouseY >= 25 && actualMouseY <= 39) {
            Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 0.3F, 0.3F));
            Messages.INSTANCE.sendToServer(new PacketCobButton(this.getMenu().getPos(), false));
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

}