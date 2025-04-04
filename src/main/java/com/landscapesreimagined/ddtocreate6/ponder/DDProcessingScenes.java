package com.landscapesreimagined.ddtocreate6.ponder;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.content.kinetics.saw.SawBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import com.simibubi.create.foundation.ponder.element.BeltItemElement;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.ParticleEmitter;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.element.ElementLink;
import net.createmod.ponder.api.element.EntityElement;
import net.createmod.ponder.api.element.WorldSectionElement;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.createmod.ponder.api.scene.Selection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.phys.Vec3;
import uwu.lopyluna.create_dd.block.BlockProperties.hydraulic_press.HydraulicPressBlockEntity;

public class DDProcessingScenes {
    public static void industrial_fan_source(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("industrial_fan_source", "Generating Rotational Force using Industrial Fans");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);

        BlockPos fan = util.grid().at(2, 2, 2);
        BlockPos lever = util.grid().at(1, 2, 2);
        BlockPos burner = util.grid().at(2, 1, 2);
        BlockPos cog = util.grid().at(3, 2, 2);

        BlockPos pos1 = util.grid().at(3, 1, 0);
        BlockPos pos2 = util.grid().at( 3, 1, 4);

        scene.world().showSection(util.select().position(burner), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().position(fan), Direction.DOWN);
        scene.idle(10);

        scene.addKeyframe();
        scene.overlay().showText(80)
                .text("Industrial Fans facing down into a source of heat can provide Rotational Force")
                .placeNearTarget()
                .pointAt(util.vector().blockSurface(fan, Direction.WEST));
        scene.idle(70);
        scene.addKeyframe();

        scene.world().showSection(util.select().position(lever), Direction.EAST);
        scene.idle(15);

        scene.world().toggleRedstonePower(util.select().position(lever));
        scene.effects().indicateRedstone(lever);
        scene.world().setKineticSpeed(util.select().position(fan), 64);
        scene.world().setKineticSpeed(util.select().position(cog), 64);
        scene.world().setKineticSpeed(util.select().fromTo(pos1, pos2), 64);
        scene.effects().rotationSpeedIndicator(fan);
        scene.idle(10);

        scene.addKeyframe();
        scene.overlay().showText(80)
                .text("When given a Strong Redstone Signal, the Industrial Fans will start providing power")
                .colored(PonderPalette.RED)
                .placeNearTarget()
                .pointAt(util.vector().blockSurface(lever, Direction.EAST));
        scene.idle(20);

        scene.world().showSection(util.select().position(cog), Direction.WEST);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(pos1, pos2), Direction.WEST);
        scene.idle(50);

        scene.markAsFinished();
    }


    public static void Motors(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("motors", "Generating Rotational Force using Motors");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);

        BlockPos motor = util.grid().at(3, 1, 2);
        BlockPos speedBox = util.grid().at(1, 1, 2);
        BlockPos flywheel = util.grid().at(1, 1, 4);
        BlockPos belt1 = util.grid().at(2, 1, 0);
        BlockPos belt2 = util.grid().at( 2, 1, 4);

        scene.world().showSection(util.select().position(motor), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(belt1, belt2), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(flywheel), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(speedBox), Direction.DOWN);

        scene.idle(10);
        scene.effects().rotationSpeedIndicator(motor);
        scene.overlay().showText(50)
                .text("Motors are a compact and configurable source of Rotational Force")
                .placeNearTarget()
                .pointAt(util.vector().topOf(motor));
        scene.idle(70);

        Vec3 blockSurface = util.vector().blockSurface(motor, Direction.NORTH)
                .add(1 / 16f, 0, 3 / 16f);
        scene.overlay().showFilterSlotInput(blockSurface, Direction.NORTH, 80);
        scene.overlay().showControls(blockSurface, Pointing.DOWN, 60).rightClick();
        scene.idle(20);

        scene.overlay().showText(60)
                .text("The generated speed can be configured on its input panels")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(blockSurface);
        scene.idle(10);
        scene.idle(50);
        scene.world().modifyKineticSpeed(util.select().position(motor), f -> 4 * f);
        scene.world().modifyKineticSpeed(util.select().position(speedBox), f -> 4 * f);
        scene.world().modifyKineticSpeed(util.select().position(flywheel), f -> 4 * f);
        scene.world().modifyKineticSpeed(util.select().fromTo(belt1, belt2), f -> 4 * f);
        scene.idle(10);

        scene.effects().rotationSpeedIndicator(motor);
    }

    public static void cogCrank(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("cog_crank", "Generating Rotational Force using Cog Cranks");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);

        BlockPos gaugePos = util.grid().at(1, 3, 3);
        BlockPos handlePos = util.grid().at(2, 1, 2);
        BlockPos pos1 = util.grid().at(1, 1, 3);

        scene.world().showSection(util.select().layer(1), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().layer(2), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().layer(3), Direction.DOWN);
        scene.idle(20);

        Vec3 centerOf = util.vector().centerOf(handlePos);
        Vec3 sideOf = centerOf.add(-0.5, 0, 0);

        scene.overlay().showText(70)
                .text("Cog Cranks can be used by players to apply rotational force manually")
                .placeNearTarget()
                .pointAt(sideOf);
        scene.idle(80);

        scene.overlay().showControls(centerOf, Pointing.DOWN, 40).rightClick();
        scene.idle(7);
        scene.world().setKineticSpeed(util.select().position(handlePos), 32);
        scene.world().setKineticSpeed(util.select().fromTo(pos1, gaugePos), -16);
        scene.effects().rotationDirectionIndicator(handlePos);
        scene.effects().indicateSuccess(gaugePos);
        scene.idle(10);
        scene.overlay().showText(50)
                .text("Hold Right-Click to rotate it Counter-Clockwise")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sideOf);

        scene.idle(35);
        scene.world().setKineticSpeed(util.select().everywhere(), 0);
        scene.idle(15);

        scene.overlay().showControls(centerOf, Pointing.DOWN,
                40).rightClick().whileSneaking();

        scene.idle(7);
        scene.world().setKineticSpeed(util.select().position(handlePos), -32);
        scene.world().setKineticSpeed(util.select().fromTo(pos1, gaugePos), 16);
        scene.effects().rotationDirectionIndicator(handlePos);
        scene.effects().indicateSuccess(gaugePos);
        scene.idle(10);
        scene.overlay().showText(90)
                .text("Sneak and Hold Right-Click to rotate it Clockwise")
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(sideOf);

        scene.idle(35);
        scene.world().setKineticSpeed(util.select().everywhere(), 0);
        scene.idle(45);
    }


    public static void fan_sails(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("fan_sails", "Using Fan Sails for Changing Encased/Industrial Fans Types");
        scene.configureBasePlate(0, 0, 9);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().layer(1), Direction.DOWN);
        scene.idle(5);
        scene.idle(20);
    }

    public static void stone_generation(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("stone_generation", "Using Fluids to Generator Stones");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);
        scene.world().showSection(util.select().layer(1), Direction.DOWN);
        scene.idle(10);
        scene.rotateCameraY(-90);
        scene.idle(5);
        scene.idle(20);
    }

    public static void bulk_pressing(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("hydraulic_press", "Bulk Processing Items with the Hydraulic Press");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);

        ElementLink<WorldSectionElement> depot =
                scene.world().showIndependentSection(util.select().position(2, 1, 1), Direction.DOWN);
        scene.world().moveSection(depot, util.vector().of(0, 0, 1), 0);
        scene.idle(10);

        Selection pressS = util.select().position(2, 3, 2);
        BlockPos pressPos = util.grid().at(2, 3, 2);
        BlockPos depotPos = util.grid().at(2, 1, 1);
        scene.world().setKineticSpeed(pressS, 0);
        scene.world().showSection(pressS, Direction.DOWN);
        scene.idle(10);

        scene.world().showSection(util.select().fromTo(2, 1, 3, 2, 1, 5), Direction.NORTH);
        scene.idle(3);
        scene.world().showSection(util.select().position(2, 2, 3), Direction.SOUTH);
        scene.idle(3);
        scene.world().showSection(util.select().position(2, 3, 3), Direction.NORTH);
        scene.world().setKineticSpeed(pressS, -32);
        scene.effects().indicateSuccess(pressPos);
        scene.idle(10);

        Vec3 pressSide = util.vector().blockSurface(pressPos, Direction.WEST);
        scene.overlay().showText(60)
                .pointAt(pressSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The Hydraulic Press can bulk process items provided beneath it");
        scene.idle(70);
        scene.overlay().showText(60)
                .pointAt(pressSide.subtract(0, 2, 0))
                .placeNearTarget()
                .text("The Input items can be dropped or placed on a Depot under the Press");
        scene.idle(50);
        ItemStack copper = new ItemStack(Items.COPPER_INGOT, 64);
        scene.world().createItemOnBeltLike(depotPos, Direction.NORTH, copper);
        Vec3 depotCenter = util.vector().centerOf(depotPos.south());
        scene.overlay().showControls(depotCenter, Pointing.UP, 30).withItem(copper);
        scene.idle(10);

        Class<HydraulicPressBlockEntity> type = HydraulicPressBlockEntity.class;
        scene.world().modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BELT));
        scene.idle(30);
        scene.world().modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), copper));
        scene.world().removeItemsFromBelt(depotPos);
        ItemStack sheet = new ItemStack(AllItems.COPPER_SHEET.asStack().getItem(), 64);
        scene.world().createItemOnBeltLike(depotPos, Direction.UP, sheet);
        scene.idle(10);
        scene.overlay().showControls(depotCenter, Pointing.UP, 50).withItem(sheet);
        scene.idle(60);

        scene.world().hideIndependentSection(depot, Direction.NORTH);
        scene.idle(5);
        scene.world().showSection(util.select().fromTo(0, 1, 3, 0, 2, 3), Direction.DOWN);
        scene.idle(10);
        scene.world().showSection(util.select().fromTo(4, 1, 2, 0, 2, 2), Direction.SOUTH);
        scene.idle(20);
        BlockPos beltPos = util.grid().at(0, 1, 2);
        scene.overlay().showText(40)
                .pointAt(util.vector().blockSurface(beltPos, Direction.WEST))
                .placeNearTarget()
                .attachKeyFrame()
                .text("When items are provided on a belt...");
        scene.idle(30);

        ElementLink<BeltItemElement> ingot = scene.world().createItemOnBelt(beltPos, Direction.SOUTH, copper);
        scene.idle(15);
        ElementLink<BeltItemElement> ingot2 = scene.world().createItemOnBelt(beltPos, Direction.SOUTH, copper);
        scene.idle(15);
        scene.world().stallBeltItem(ingot, true);
        scene.world().modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BELT));

        scene.overlay().showText(50)
                .pointAt(pressSide)
                .placeNearTarget()
                .attachKeyFrame()
                .text("The Press will hold and bulk process them automatically");

        scene.idle(30);
        scene.world().modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), copper));
        scene.world().removeItemsFromBelt(pressPos.below(2));
        ingot = scene.world().createItemOnBelt(pressPos.below(2), Direction.UP, sheet);
        scene.world().stallBeltItem(ingot, true);
        scene.idle(15);
        scene.world().stallBeltItem(ingot, false);
        scene.idle(15);
        scene.world().stallBeltItem(ingot2, true);
        scene.world().modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .start(PressingBehaviour.Mode.BELT));
        scene.idle(30);
        scene.world().modifyBlockEntity(pressPos, type, pte -> pte.getPressingBehaviour()
                .makePressingParticleEffect(depotCenter.add(0, 8 / 16f, 0), copper));
        scene.world().removeItemsFromBelt(pressPos.below(2));
        ingot2 = scene.world().createItemOnBelt(pressPos.below(2), Direction.UP, sheet);
        scene.world().stallBeltItem(ingot2, true);
        scene.idle(15);
        scene.world().stallBeltItem(ingot2, false);

    }


    public static void processing(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        scene.title("bronze_saw_processing", "Bulk Processing Items on the Bronze Saw");
        scene.configureBasePlate(0, 0, 5);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        BlockPos shaftPos = util.grid().at(2, 1, 3);
        scene.world().setBlock(shaftPos, AllBlocks.SHAFT.getDefaultState()
                .setValue(ShaftBlock.AXIS, Direction.Axis.Z), false);

        BlockPos sawPos = util.grid().at(2, 1, 2);
        Selection sawSelect = util.select().position(sawPos);
        scene.world().modifyBlockEntityNBT(sawSelect, SawBlockEntity.class, nbt -> nbt.putInt("RecipeIndex", 0));

        scene.idle(5);
        scene.world().showSection(util.select().fromTo(2, 1, 3, 2, 1, 5), Direction.DOWN);
        scene.idle(10);
        scene.effects().rotationDirectionIndicator(shaftPos);
        scene.world().showSection(sawSelect, Direction.DOWN);
        scene.idle(10);
        scene.overlay().showText(50)
                .attachKeyFrame()
                .text("Upward facing Bronze Saws can bulk process a variety of items")
                .pointAt(util.vector().blockSurface(sawPos, Direction.WEST))
                .placeNearTarget();
        scene.idle(45);

        ItemStack log = new ItemStack(Items.OAK_LOG, 16);
        ItemStack strippedLog = new ItemStack(Items.STRIPPED_OAK_LOG, 16);
        ItemStack planks = new ItemStack(Items.OAK_PLANKS, 16);

        Vec3 itemSpawn = util.vector().centerOf(sawPos.above()
                .west());
        ElementLink<EntityElement> logItem = scene.world().createItemEntity(itemSpawn, util.vector().of(0, 0, 0), log);
        scene.idle(12);

        scene.overlay().showControls(itemSpawn, Pointing.DOWN, 20).withItem(log);
        scene.idle(10);

        scene.world().modifyEntity(logItem, e -> e.setDeltaMovement(util.vector().of(0.05, 0.2, 0)));
        scene.idle(12);

        scene.world().modifyEntity(logItem, Entity::discard);
        scene.world().createItemOnBeltLike(sawPos, Direction.WEST, log);
        scene.idle(60);

        logItem = scene.world().createItemEntity(util.vector().topOf(sawPos)
                .add(0.5, -.1, 0), util.vector().of(0.05, 0.18, 0), strippedLog);
        scene.idle(12);
        scene.overlay().showControls(itemSpawn.add(2, 0, 0), Pointing.DOWN,
                20).withItem(strippedLog);
        scene.idle(30);

        scene.overlay().showText(60)
                .attachKeyFrame()
                .text("The processed item always moves against the rotational input to the saw")
                .pointAt(util.vector().blockSurface(sawPos, Direction.UP))
                .placeNearTarget();
        scene.idle(70);

        scene.world().modifyKineticSpeed(util.select().everywhere(), f -> -2 * f);
        scene.effects().rotationDirectionIndicator(shaftPos);
        scene.world().modifyEntity(logItem, e -> e.setDeltaMovement(util.vector().of(-0.05, 0.2, 0)));
        scene.idle(12);

        scene.world().modifyEntity(logItem, Entity::discard);
        scene.world().createItemOnBeltLike(sawPos, Direction.EAST, strippedLog);
        scene.idle(60);

        logItem = scene.world().createItemEntity(util.vector().topOf(sawPos)
                .add(-0.5, -.1, 0), util.vector().of(-0.05, 0.18, 0), planks);
        scene.idle(22);

        Selection otherBelt = util.select().fromTo(3, 1, 3, 4, 1, 2);
        Selection belt = util.select().fromTo(0, 1, 2, 1, 1, 3);

        scene.world().modifyEntity(logItem, Entity::discard);
        scene.world().setKineticSpeed(otherBelt, 0);
        scene.world().setKineticSpeed(belt, 0);
        scene.world().modifyKineticSpeed(util.select().everywhere(), f -> -f);
        scene.world().setBlock(shaftPos, AllBlocks.COGWHEEL.getDefaultState()
                .setValue(ShaftBlock.AXIS, Direction.Axis.Z), true);
        scene.idle(3);
        scene.addKeyframe();

        scene.world().multiplyKineticSpeed(util.select().everywhere(), .5f);

        ElementLink<WorldSectionElement> beltSection = scene.world().showIndependentSection(belt, Direction.EAST);
        scene.world().moveSection(beltSection, util.vector().of(0, 100, 0), 0);
        scene.idle(1);
        scene.world().removeItemsFromBelt(util.grid().at(1, 1, 2));
        scene.idle(1);
        scene.world().setKineticSpeed(belt, -64);
        scene.idle(1);
        scene.world().moveSection(beltSection, util.vector().of(0, -100, 0), 0);
        scene.idle(3);

        ElementLink<WorldSectionElement> otherBeltSection =
                scene.world().showIndependentSection(otherBelt, Direction.WEST);
        scene.world().moveSection(otherBeltSection, util.vector().of(0, 100, 0), 0);
        scene.idle(1);
        scene.world().removeItemsFromBelt(util.grid().at(3, 1, 2));
        scene.idle(1);
        scene.world().setKineticSpeed(otherBelt, -64);
        scene.idle(1);
        scene.world().moveSection(otherBeltSection, util.vector().of(0, -100, 0), 0);
        scene.idle(3);

        ItemStack stone = new ItemStack(Blocks.STONE, 16);
        BlockPos firstBelt = util.grid().at(0, 1, 2);
        scene.overlay().showText(60)
                .text("Saws can work in-line with Mechanical Belts")
                .pointAt(util.vector().blockSurface(firstBelt, Direction.WEST))
                .placeNearTarget();
        scene.idle(40);
        scene.world().createItemOnBelt(firstBelt, Direction.WEST, stone);

        scene.idle(40);
        Vec3 filter = util.vector().of(2.5, 1 + 13 / 16f, 2 + 5 / 16f);
        scene.overlay().showFilterSlotInput(filter, Direction.UP, 80);
        scene.overlay().showText(80)
                .attachKeyFrame()
                .text("When an ingredient has multiple possible outcomes, the filter slot can specify it")
                .pointAt(filter)
                .placeNearTarget();
        scene.idle(90);

        ItemStack bricks = new ItemStack(Blocks.STONE_BRICKS, 16);
        scene.overlay().showControls(filter, Pointing.DOWN, 30).withItem(bricks);
        scene.world().modifyEntities(ItemEntity.class, Entity::discard);
        scene.idle(7);
        scene.world().setFilterData(util.select().position(sawPos), SawBlockEntity.class, bricks);
        scene.idle(10);
        scene.world().createItemOnBelt(firstBelt, Direction.WEST, stone);
        scene.idle(50);

        scene.markAsFinished();
        scene.overlay().showText(100)
                .text("Without filter, the Saw would cycle through all outcomes instead")
                .colored(PonderPalette.RED)
                .pointAt(filter)
                .placeNearTarget();
        scene.idle(65);
        scene.world().modifyEntities(ItemEntity.class, Entity::discard);
    }



    public static void furnaceEngine(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        furnaceEngine(scene, util, false);
    }

    public static void flywheel(SceneBuilder default_scene, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(default_scene);
        furnaceEngine(scene, util, true);
    }

    private static void furnaceEngine(SceneBuilder passed_scene, SceneBuildingUtil util, boolean flywheel) {
        CreateSceneBuilder scene = new CreateSceneBuilder(passed_scene);
        scene.title(flywheel ? "flywheel" : "furnace_engine",
                "Generating Rotational Force using the " + (flywheel ? "Flywheel Component" : "Flywheel Engine"));
        scene.configureBasePlate(0, 0, 7);
        scene.world().showSection(util.select().layer(0), Direction.UP);

        BlockPos furnacePos = util.grid().at(4, 2, 3);
        BlockPos flywheelbasePos = util.grid().at(4, 1, 3);
        BlockPos base1 = util.grid().at(3, 1, 2);
        BlockPos base2 = util.grid().at(5, 1, 2);
        BlockPos cogPos = util.grid().at(1, 2, 2);
        BlockPos gaugePos = util.grid().at(1, 2, 1);
        BlockPos shaftPos = util.grid().at(1, 2, 0);

        scene.idle(5);
        scene.world().showSection(util.select().fromTo(base1, base2), Direction.DOWN);
        scene.idle(2);
        scene.world().showSection(util.select().fromTo(base1.south(1), base2.south(1)), Direction.DOWN);
        scene.idle(2);
        Selection furnaceSelect = util.select().position(furnacePos);
        scene.world().showSection(furnaceSelect, Direction.DOWN);
        scene.idle(2);
        scene.world().showSection(util.select().position(furnacePos.west()), Direction.DOWN);
        scene.idle(8);
        scene.world().showSection(util.select().position(flywheelbasePos.west(3)), Direction.EAST);
        scene.idle(2);
        scene.world().showSection(util.select().position(furnacePos.west(3)), Direction.EAST);
        scene.idle(8);

        String text = flywheel ? "Flywheel Components are required for generating rotational force with the Flywheel Engine"
                : "Flywheel Engines generate Rotational Force while their attached Furnace is running";
        scene.overlay().showText(50)
                .attachKeyFrame()
                .placeNearTarget()
                .pointAt(util.vector().topOf(furnacePos.west(flywheel ? 3 : 1)))
                .text(text);
        scene.idle(60);

        scene.addKeyframe();
        scene.overlay().showControls(
                util.vector().topOf(furnacePos), Pointing.DOWN,
                30).withItem(new ItemStack(Items.OAK_LOG));
        scene.idle(5);
        scene.overlay().showControls(util.vector().blockSurface(furnacePos, Direction.NORTH), Pointing.RIGHT, 30)
                .withItem(new ItemStack(Items.COAL));
        scene.idle(7);
        scene.world().cycleBlockProperty(furnacePos, FurnaceBlock.LIT);
        ParticleEmitter lava = scene.effects().simpleParticleEmitter(ParticleTypes.LAVA, Vec3.ZERO);
        scene.effects().emitParticles(util.vector().of(4.5, 2.2, 2.9), lava, 4, 1);
        scene.world().setKineticSpeed(util.select().fromTo(1, 2, 3, 1, 2, 0), 24);
        scene.idle(40);

        scene.world().showSection(util.select().position(cogPos), Direction.SOUTH);
        scene.idle(8);
        scene.effects().rotationSpeedIndicator(cogPos);
        scene.world().showSection(util.select().position(gaugePos.below(1)), Direction.SOUTH);
        scene.idle(2);
        scene.world().showSection(util.select().position(gaugePos), Direction.SOUTH);
        scene.idle(8);
        scene.world().showSection(util.select().position(shaftPos), Direction.SOUTH);
        scene.idle(8);
        scene.effects().rotationSpeedIndicator(shaftPos);

        scene.overlay().showText(40)
                .attachKeyFrame()
                .placeNearTarget()
                .colored(PonderPalette.GREEN)
                .pointAt(util.vector().blockSurface(gaugePos, Direction.WEST))
                .text("The provided Rotational Force has a decently large stress capacity unlike the Steam Engine");
        scene.idle(20);



    }

}
