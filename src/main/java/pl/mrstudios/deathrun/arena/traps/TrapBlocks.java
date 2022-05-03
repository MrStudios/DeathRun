package pl.mrstudios.deathrun.arena.traps;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import pl.mrstudios.deathrun.arena.objects.Block;
import pl.mrstudios.deathrun.arena.objects.Location;
import pl.mrstudios.deathrun.arena.objects.Trap;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TrapBlocks extends Trap {

    private List<Block> blocksBackup = new ArrayList<>();

    public TrapBlocks() { super(Type.BLOCKS); }

    @Override
    public void onStart() {

        super.onStart();

        for (Location location : this.getTrapLocations())
            if (!location.bukkit().getBlock().getType().equals(Material.AIR))
                this.blocksBackup.add(new Block(location, location.bukkit().getBlock().getType(), location.bukkit().getBlock().getData()));

        for (Block block : this.blocksBackup)
            block.getLocation().bukkit().getBlock().setType(Material.AIR);

    }

    @Override
    public void onEnd() {

        super.onEnd();

        for (Block block : this.blocksBackup)
            block.getLocation().bukkit().getBlock().setTypeIdAndData(block.getMaterial().getId(), block.getData(), false);

        this.blocksBackup.clear();

    }

}
