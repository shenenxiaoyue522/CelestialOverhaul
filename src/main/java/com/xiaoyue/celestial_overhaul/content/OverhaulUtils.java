package com.xiaoyue.celestial_overhaul.content;

import com.xiaoyue.celestial_overhaul.data.COModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class OverhaulUtils {

	public static boolean canGrassPierce(@Nullable Player attacker, Level level, BlockPos pos) {
		if (attacker == null) return false;
		if (COModConfig.COMMON.grassPiercingAttack.get()) {
			BlockState state = level.getBlockState(pos);
			return state.getCollisionShape(level, pos).isEmpty();
		}
		return false;
	}

	@Nullable
	public static EntityHitResult rayTraceEntity(Player player, float partialTick, double distance, Predicate<Entity> predicate) {
		Vec3 fromPosition = player.getEyePosition(partialTick);
		Vec3 vector = player.getViewVector(partialTick);
		Vec3 toPosition = fromPosition.add(vector.x * distance, vector.y * distance, vector.z * distance);
		BlockHitResult result = player.level().clip(new ClipContext(fromPosition, toPosition, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
		if (!result.getType().equals(HitResult.Type.MISS)) {
			toPosition = result.getLocation();
		}
		AABB box = new AABB(fromPosition, toPosition);
		return ProjectileUtil.getEntityHitResult(player.level(), player, fromPosition, toPosition, box, predicate);
	}

	public static List<Entity> getVehicle(Player player) {
		List<Entity> list = new ArrayList<>();
		Entity entity = player;
		while (entity.isPassenger()) {
			entity = entity.getVehicle();
			list.add(entity);
		}
		return list;
	}
}
