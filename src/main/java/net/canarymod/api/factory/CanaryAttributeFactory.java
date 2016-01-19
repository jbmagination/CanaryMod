package net.canarymod.api.factory;

import net.canarymod.api.attributes.Attribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityHorse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason (darkdiplomat)
 */
public class CanaryAttributeFactory implements AttributeFactory {
    private final Map<String, Attribute> mappedGeneric; // Cause there is no map for it

    CanaryAttributeFactory() {
        mappedGeneric = new HashMap<String, Attribute>();
        mappedGeneric.put("generic.maxHealth", SharedMonsterAttributes.a.getWrapper());
        mappedGeneric.put("generic.followRange", SharedMonsterAttributes.b.getWrapper());
        mappedGeneric.put("generic.knockbackResistance", SharedMonsterAttributes.c.getWrapper());
        mappedGeneric.put("generic.movementSpeed", SharedMonsterAttributes.d.getWrapper());
        mappedGeneric.put("generic.attackDamage", SharedMonsterAttributes.e.getWrapper());

        mappedGeneric.put("horse.jumpStrength", EntityHorse.br.getWrapper());

        mappedGeneric.put("zombie.spawnReinforcements", EntityZombie.b.getWrapper());
    }

    @Override
    public Attribute getGenericAttribute(String name) {
        return mappedGeneric.get(name);
    }
}
