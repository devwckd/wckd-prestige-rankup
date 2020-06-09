package me.devwckd.prestigerankup.entity;

import com.googlecode.cqengine.attribute.Attribute;
import lombok.Builder;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.googlecode.cqengine.query.QueryFactory.*;

@Data
@Builder
public class Rank {

    public static final Attribute<Rank, Integer> RANK_POSITION = attribute("rankPosition", Rank::getPosition);
    public static final Attribute<Rank, String> RANK_ID = attribute("rankId", Rank::getId);

    private final int position;
    private final String id;
    private final String name;
    private final double price;

    private final ItemStack icon;
    private final ItemStack completedIcon;

    private final List<String> commandsIn;
    private final List<String> commandsOut;

}
