package com.xiaoyue.celestial_overhaul.data;

import com.xiaoyue.celestial_overhaul.CelestialOverhaul;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class COTagGen {

	public static final TagKey<Item> CAN_BLOCK_SWORD = ItemTags.create(CelestialOverhaul.loc("can_block_sword"));
	public static final TagKey<Item> CANNOT_BLOCK_SWORD = ItemTags.create(CelestialOverhaul.loc("cannot_block_sword"));

	public static final TagKey<EntityType<?>> DAMAGE_SCALING_BLACK_LIST = TagKey.create(Registries.ENTITY_TYPE, CelestialOverhaul.loc("damage_scaling_black_list"));

	public static boolean allow(Entity e) {
		if (COModConfig.COMMON.scalingToPlayerOnly.get()) {
			return e instanceof Player;
		}
		return !e.getType().is(DAMAGE_SCALING_BLACK_LIST);
	}
}
