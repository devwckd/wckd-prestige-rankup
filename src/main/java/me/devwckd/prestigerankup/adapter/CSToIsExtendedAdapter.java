package me.devwckd.prestigerankup.adapter;

import co.wckd.boilerplate.adapter.CSToISAdapter;
import me.devwckd.prestigerankup.util.HeadUtils;
import org.bukkit.Material;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class CSToIsExtendedAdapter extends CSToISAdapter {

    @Override
    public ItemStack adapt(MemorySection from) {

        ItemStack adapted = super.adapt(from);
        if(adapted == null) return null;

        if(adapted.getType() != Material.SKULL_ITEM || adapted.getDurability() != 3) return adapted;

        String owner = from.getString("owner");
        if(owner != null) {
            SkullMeta skullMeta = ((SkullMeta) adapted.getItemMeta());
            skullMeta.setOwner(owner);
            adapted.setItemMeta(skullMeta);
            return adapted;
        }

        String skin = from.getString("skin");
        if(skin != null) {
            return HeadUtils.injectSkin(adapted, skin);
        }

        return adapted;

    }
}
