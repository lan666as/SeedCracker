package kaptainwutax.seedcracker.cracker.population;

import kaptainwutax.seedcracker.cracker.storage.DataStorage;
import kaptainwutax.seedcracker.cracker.storage.TimeMachine;
import kaptainwutax.seedcracker.util.Rand;
import kaptainwutax.seedcracker.util.math.LCG;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.stream.Collectors;

public class EmeraldOreData extends DecoratorData {

    private static final double BITS = Math.log(28 * 16 * 16 * 0.5D) / Math.log(2);
    public static final int SALT = 40014;

    private static final LCG[] SKIP = {
            Rand.JAVA_LCG.combine(0),
            Rand.JAVA_LCG.combine(1),
            Rand.JAVA_LCG.combine(2),
            Rand.JAVA_LCG.combine(3)
    };

    private List<BlockPos> starts;

    public EmeraldOreData(ChunkPos chunkPos, Biome biome, List<BlockPos> ores) {
        super(chunkPos, SALT, biome);

        this.starts = ores.stream().map(pos ->
            new BlockPos(pos.getX() & 15, pos.getY(), pos.getZ() & 15)
        ).collect(Collectors.toList());
    }

    @Override
    public boolean testDecorator(Rand rand) {
        if(this.starts.isEmpty())return true;

        //TODO: This currently only supports 1 emerald per chunk.
        BlockPos start = this.starts.get(0);

        int b = rand.nextInt(6);

        for(int i = 0; i < b + 3; i++) {
            int x = rand.nextInt(16);
            int z = rand.nextInt(16);
            int y = rand.nextInt(28) + 4;

            if(y == start.getY() && x == start.getX() && z == start.getZ()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double getBits() {
        return BITS;
    }

    @Override
    public void onDataAdded(DataStorage dataStorage) {
        dataStorage.getTimeMachine().poke(TimeMachine.Phase.STRUCTURES);
    }

}
