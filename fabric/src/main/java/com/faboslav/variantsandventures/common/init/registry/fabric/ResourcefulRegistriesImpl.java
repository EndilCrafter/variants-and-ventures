package com.faboslav.variantsandventures.common.init.registry.fabric;

import com.faboslav.variantsandventures.common.init.registry.CustomRegistryLookup;
import com.faboslav.variantsandventures.common.init.registry.ResourcefulRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.SimpleRegistry;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class ResourcefulRegistriesImpl
{
	public static <T> ResourcefulRegistry<T> create(Registry<T> registry, String id) {
		return new FabricCustomResourcefulRegistry<>(registry, id);
	}

	public static <T, R extends T, K extends Registry<T>> Pair<Supplier<CustomRegistryLookup<T, T>>, ResourcefulRegistry<T>> createCustomRegistryInternal(
		String modId,
		RegistryKey<K> key,
		boolean save,
		boolean sync,
		boolean allowModification
	) {
		FabricRegistryBuilder<T, SimpleRegistry<T>> registry = FabricRegistryBuilder.createSimple(null, key.getValue());
		if (sync) registry.attribute(RegistryAttribute.SYNCED);
		if (allowModification) registry.attribute(RegistryAttribute.MODDED);
		SimpleRegistry<T> builtRegistry = registry.buildAndRegister();
		FabricCustomRegistry<T> customRegistry = new FabricCustomRegistry<>(builtRegistry);
		return Pair.of(() -> customRegistry, new FabricCustomResourcefulRegistry<>(builtRegistry, modId));
	}
}
