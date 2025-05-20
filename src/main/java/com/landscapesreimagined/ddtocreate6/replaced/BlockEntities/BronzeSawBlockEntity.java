package com.landscapesreimagined.ddtocreate6.replaced.BlockEntities;

import com.google.common.collect.ImmutableList;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.kinetics.saw.SawFilterSlot;
import com.simibubi.create.content.kinetics.saw.TreeCutter;
import com.simibubi.create.content.processing.recipe.ProcessingInventory;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import uwu.lopyluna.create_dd.DDTags;
import uwu.lopyluna.create_dd.block.BlockProperties.drill.bronze.BronzeDrillBlock;
import uwu.lopyluna.create_dd.configs.DDConfigs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BronzeSawBlockEntity extends SawBlockEntity {
    private static final Object cuttingRecipesKey = new Object();
    private int recipeIndex;
    private final LazyOptional<IItemHandler> invProvider;
    private FilteringBehaviour filtering;
    private ItemStack playEvent;

    public BronzeSawBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.inventory = new ProcessingInventory(this::start).withSlotLimit(!DDConfigs.server().recipes.bulkCutting.get());
        this.inventory.remainingTime = -1.0F;
        this.recipeIndex = 0;
        this.invProvider = LazyOptional.of(() -> this.inventory);
        this.playEvent = ItemStack.EMPTY;
    }

    public void write(CompoundTag compound, boolean clientPacket) {
        compound.put("Inventory", this.inventory.serializeNBT());
        compound.putInt("RecipeIndex", this.recipeIndex);
        super.write(compound, clientPacket);
        if (clientPacket && !this.playEvent.isEmpty()) {
            compound.put("PlayEvent", this.playEvent.serializeNBT());
            this.playEvent = ItemStack.EMPTY;
        }
    }

    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        this.recipeIndex = compound.getInt("RecipeIndex");
        if (compound.contains("PlayEvent")) {
            this.playEvent = ItemStack.of(compound.getCompound("PlayEvent"));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void tickAudio() {
        super.tickAudio();
        if (this.getSpeed() != 0.0F) {
            if (!this.playEvent.isEmpty()) {
                boolean isWood = false;
                Item item = this.playEvent.getItem();
                if (item instanceof BlockItem) {
                    Block block = ((BlockItem)item).getBlock();
                    isWood = block.getSoundType(block.defaultBlockState(), this.level, this.worldPosition, null) == SoundType.WOOD;
                }

                this.spawnEventParticles(this.playEvent);
                this.playEvent = ItemStack.EMPTY;
                if (!isWood) {
                    AllSoundEvents.SAW_ACTIVATE_STONE.playAt(this.level, this.worldPosition, 3.0F, 1.0F, true);
                } else {
                    AllSoundEvents.SAW_ACTIVATE_WOOD.playAt(this.level, this.worldPosition, 3.0F, 1.0F, true);
                }
            }
        }
    }

    protected BlockPos getBreakingPos() {
        return this.getBlockPos().relative((Direction)this.getBlockState().getValue(BronzeDrillBlock.FACING));
    }

    protected float getBreakSpeed() {
        return Math.abs(this.getSpeed() / 35.0F);
    }

    public boolean canBreak(BlockState stateToBreak, float blockHardness) {
        boolean sawable = isSawable(stateToBreak);
        return super.canBreak(stateToBreak, blockHardness) && sawable;
    }

    public static boolean isSawable(BlockState stateToBreak) {
        if (stateToBreak.is(BlockTags.SAPLINGS)) {
            return false;
        } else if (TreeCutter.isLog(stateToBreak) || stateToBreak.is(BlockTags.LEAVES)) {
            return true;
        } else if (TreeCutter.isRoot(stateToBreak)) {
            return true;
        } else {
            Block block = stateToBreak.getBlock();
            if (block instanceof BambooStalkBlock) {
                return true;
            } else if (block instanceof StemGrownBlock) {
                return true;
            } else if (block instanceof CactusBlock) {
                return true;
            } else if (block instanceof SugarCaneBlock) {
                return true;
            } else if (block instanceof KelpPlantBlock) {
                return true;
            } else if (block instanceof KelpBlock) {
                return true;
            } else if (block instanceof ChorusPlantBlock) {
                return true;
            } else if (TreeCutter.canDynamicTreeCutFrom(block)) {
                return true;
            } else {
                return !stateToBreak.is(DDTags.AllBlockTags.bronze_saw_immune.tag) && stateToBreak.is(DDTags.AllBlockTags.bronze_saw_valid.tag);
            }
        }
    }

    public void tick() {
        if (this.shouldRun() && this.ticksUntilNextProgress < 0) {
            this.destroyNextTick();
        }

        super.tick();
        if (this.canProcess()) {
            if (this.getSpeed() != 0.0F) {
                if (this.inventory.remainingTime == -1.0F) {
                    if (!this.inventory.isEmpty() && !this.inventory.appliedRecipe) {
                        this.start(this.inventory.getStackInSlot(0));
                    }
                } else {
                    float processingSpeed = Mth.clamp(Math.abs(this.getSpeed()) / 24.0F, 1.0F, 128.0F);
                    this.inventory.remainingTime -= processingSpeed;
                    if (this.inventory.remainingTime > 0.0F) {
                        this.spawnParticles(this.inventory.getStackInSlot(0));
                    }

                    if (!(this.inventory.remainingTime < 5.0F) || this.inventory.appliedRecipe) {
                        Vec3 itemMovement = this.getItemMovementVec();
                        Direction itemMovementFacing = Direction.getNearest(itemMovement.x, itemMovement.y, itemMovement.z);
                        if (!(this.inventory.remainingTime > 0.0F)) {
                            this.inventory.remainingTime = 0.0F;

                            for (int slot = 0; slot < this.inventory.getSlots(); slot++) {
                                ItemStack stack = this.inventory.getStackInSlot(slot);
                                if (!stack.isEmpty()) {
                                    ItemStack tryExportingToBeltFunnel = ((DirectBeltInputBehaviour)this.getBehaviour(DirectBeltInputBehaviour.TYPE))
                                            .tryExportingToBeltFunnel(stack, itemMovementFacing.getOpposite(), false);
                                    if (tryExportingToBeltFunnel != null) {
                                        if (tryExportingToBeltFunnel.getCount() != stack.getCount()) {
                                            this.inventory.setStackInSlot(slot, tryExportingToBeltFunnel);
                                            this.notifyUpdate();
                                            return;
                                        }

                                        if (!tryExportingToBeltFunnel.isEmpty()) {
                                            return;
                                        }
                                    }
                                }
                            }

                            BlockPos nextPos = this.worldPosition.offset(BlockPos.containing(itemMovement));
                            DirectBeltInputBehaviour behaviour = (DirectBeltInputBehaviour) BlockEntityBehaviour.get(this.level, nextPos, DirectBeltInputBehaviour.TYPE);
                            if (behaviour != null) {
                                boolean changed = false;
                                if (behaviour.canInsertFromSide(itemMovementFacing)) {
                                    if (!this.level.isClientSide || this.isVirtual()) {
                                        for (int slotx = 0; slotx < this.inventory.getSlots(); slotx++) {
                                            ItemStack stack = this.inventory.getStackInSlot(slotx);
                                            if (!stack.isEmpty()) {
                                                ItemStack remainder = behaviour.handleInsertion(stack, itemMovementFacing, false);
                                                if (!remainder.equals(stack, false)) {
                                                    this.inventory.setStackInSlot(slotx, remainder);
                                                    changed = true;
                                                }
                                            }
                                        }

                                        if (changed) {
                                            this.setChanged();
                                            this.sendData();
                                        }
                                    }
                                }
                            } else {
                                Vec3 outPos = VecHelper.getCenterOf(this.worldPosition).add(itemMovement.scale(0.5).add(0.0, 0.5, 0.0));
                                Vec3 outMotion = itemMovement.scale(0.0625).add(0.0, 0.125, 0.0);

                                for (int slotxx = 0; slotxx < this.inventory.getSlots(); slotxx++) {
                                    ItemStack stack = this.inventory.getStackInSlot(slotxx);
                                    if (!stack.isEmpty()) {
                                        ItemEntity entityIn = new ItemEntity(this.level, outPos.x, outPos.y, outPos.z, stack);
                                        entityIn.setDeltaMovement(outMotion);
                                        this.level.addFreshEntity(entityIn);
                                    }
                                }

                                this.inventory.clear();
                                this.level.updateNeighbourForOutputSignal(this.worldPosition, this.getBlockState().getBlock());
                                this.inventory.remainingTime = -1.0F;
                                this.sendData();
                            }
                        }
                    } else if (!this.level.isClientSide || this.isVirtual()) {
                        this.playEvent = this.inventory.getStackInSlot(0);
                        this.applyRecipe();
                        this.inventory.appliedRecipe = true;
                        this.inventory.recipeDuration = 20.0F;
                        this.inventory.remainingTime = 20.0F;
                        this.sendData();
                    }
                }
            }
        }
    }

    public void invalidate() {
        super.invalidate();
        this.invProvider.invalidate();
    }

    public <T> @NotNull LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ForgeCapabilities.ITEM_HANDLER && side != Direction.DOWN ? this.invProvider.cast() : super.getCapability(cap, side);
    }

    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        this.filtering = new FilteringBehaviour(this, new SawFilterSlot()).forRecipes();
        behaviours.add(this.filtering);
        behaviours.add(new DirectBeltInputBehaviour(this).allowingBeltFunnelsWhen(this::canProcess));
        this.registerAwardables(behaviours, new CreateAdvancement[]{AllAdvancements.SAW_PROCESSING});
    }

    private void applyRecipe() {
        List<? extends Recipe<?>> recipes = this.getRecipes();
        if (!recipes.isEmpty()) {
            if (this.recipeIndex >= recipes.size()) {
                this.recipeIndex = 0;
            }

            Recipe<?> recipe = (Recipe<?>)recipes.get(this.recipeIndex);
            int rolls = this.inventory.getStackInSlot(0).getCount();
            this.inventory.clear();
            List<ItemStack> list = new ArrayList<>();

            for (int roll = 0; roll < rolls; roll++) {
                List<ItemStack> results = new LinkedList<>();
                if (recipe instanceof CuttingRecipe) {
                    results = ((CuttingRecipe)recipe).rollResults();
                } else if (recipe instanceof StonecutterRecipe || recipe.getType() == woodcuttingRecipeType.get()) {
                    results.add(recipe.getResultItem(this.level.registryAccess()).copy());
                }

                for (int i = 0; i < results.size(); i++) {
                    ItemStack stack = results.get(i);
                    ItemHelper.addToList(stack, list);
                }
            }

            for (int slot = 0; slot < list.size() && slot + 1 < this.inventory.getSlots(); slot++) {
                this.inventory.setStackInSlot(slot + 1, list.get(slot));
            }

            this.award(AllAdvancements.SAW_PROCESSING);
        }
    }

    private List<? extends Recipe<?>> getRecipes() {
        Optional<CuttingRecipe> assemblyRecipe = SequencedAssemblyRecipe.getRecipe(
                this.level, this.inventory.getStackInSlot(0), AllRecipeTypes.CUTTING.getType(), CuttingRecipe.class
        );
        if (assemblyRecipe.isPresent() && this.filtering.test(assemblyRecipe.get().getResultItem(this.level.registryAccess()))) {
            return ImmutableList.of(assemblyRecipe.get());
        } else {
            Predicate<Recipe<?>> types = RecipeConditions.isOfType(
                    new RecipeType[]{
                            AllRecipeTypes.CUTTING.getType(),
                            DDConfigs.server().recipes.allowStonecuttingOnSaw.get() ? RecipeType.STONECUTTING : null,
                            DDConfigs.server().recipes.allowWoodcuttingOnSaw.get() ? woodcuttingRecipeType.get() : null
                    }
            );
            List<Recipe<?>> startedSearch = RecipeFinder.get(cuttingRecipesKey, this.level, types);
            return startedSearch.stream()
                    .filter(RecipeConditions.outputMatchesFilter(this.filtering))
                    .filter(RecipeConditions.firstIngredientMatches(this.inventory.getStackInSlot(0)))
                    .filter(r -> !AllRecipeTypes.shouldIgnoreInAutomation(r))
                    .collect(Collectors.toList());
        }
    }

    public void start(ItemStack inserted) {
        if (this.canProcess()) {
            if (!this.inventory.isEmpty()) {
                if (!this.level.isClientSide || this.isVirtual()) {
                    List<? extends Recipe<?>> recipes = this.getRecipes();
                    boolean valid = !recipes.isEmpty();
                    int time = 50;
                    if (recipes.isEmpty()) {
                        this.inventory.remainingTime = this.inventory.recipeDuration = 10.0F;
                        this.inventory.appliedRecipe = false;
                        this.sendData();
                    } else {
                        if (valid) {
                            this.recipeIndex++;
                            if (this.recipeIndex >= recipes.size()) {
                                this.recipeIndex = 0;
                            }
                        }

                        Recipe<?> recipe = recipes.get(this.recipeIndex);
                        if (recipe instanceof CuttingRecipe) {
                            time = ((CuttingRecipe)recipe).getProcessingDuration();
                        }

                        this.inventory.remainingTime = time * Math.max(1, inserted.getCount() / 5);
                        this.inventory.recipeDuration = this.inventory.remainingTime;
                        this.inventory.appliedRecipe = false;
                        this.sendData();
                    }
                }
            }
        }
    }
}
