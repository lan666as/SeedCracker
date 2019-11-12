package kaptainwutax.seedcracker.cracker;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.ChunkRandom;

public class StructureData {

    private int regionX;
    private int regionZ;
    private int offsetX;
    private int offsetZ;
    private Feature feature;

    public StructureData(ChunkPos chunkPos, Feature feature) {
        this.feature = feature;
        this.feature.buildCalls(this, chunkPos);
    }

    public int getRegionX() {
        return this.regionX;
    }

    public int getRegionZ() {
        return this.regionZ;
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public int getOffsetZ() {
        return this.offsetZ;
    }

    public int getSalt() {
        return this.feature.salt;
    }

    public boolean test(ChunkRandom rand) {
        return this.feature.test(rand, this.offsetX, this.offsetZ);
    }

    public abstract static class Feature {
        public final int salt;
        public final int templeDistance;

        public Feature(int salt, int templeDistance) {
            this.salt = salt;
            this.templeDistance = templeDistance;
        }

        public void buildCalls(StructureData data, ChunkPos chunkPos) {
            int chunkX = chunkPos.x;
            int chunkZ = chunkPos.z;

            chunkX = chunkX < 0 ? chunkX - this.templeDistance + 1 : chunkX;
            chunkZ = chunkZ < 0 ? chunkZ - this.templeDistance + 1 : chunkZ;

            //Pick out in which region the chunk is.
            int regionX = (chunkX / this.templeDistance);
            int regionZ = (chunkZ / this.templeDistance);

            data.regionX = regionX;
            data.regionZ = regionZ;

            regionX *= this.templeDistance;
            regionZ *= this.templeDistance;

            data.offsetX = chunkPos.x - regionX;
            data.offsetZ = chunkPos.z - regionZ;
        }

        public abstract boolean test(ChunkRandom rand, int x, int z);
    }


    public static final Feature SWAMP_HUT = new Feature(14357620, 32) {
        @Override
        public boolean test(ChunkRandom rand, int x, int z) {
            return rand.nextInt(24) == x && rand.nextInt(24) == z;
        }
    };

    public static final Feature DESERT_PYRAMID = new Feature(14357617, 32) {
        @Override
        public boolean test(ChunkRandom rand, int x, int z) {
            return rand.nextInt(24) == x && rand.nextInt(24) == z;
        }
    };

    public static final Feature JUNGLE_TEMPLE = new Feature(14357619, 32) {
        @Override
        public boolean test(ChunkRandom rand, int x, int z) {
            return rand.nextInt(24) == x && rand.nextInt(24) == z;
        }
    };

    public static final Feature END_CITY = new Feature(10387313, 20) {
        @Override
        public boolean test(ChunkRandom rand, int x, int z) {
            return (rand.nextInt(9) + rand.nextInt(9)) / 2 == x
                    && (rand.nextInt(9) + rand.nextInt(9)) / 2 == z;
        }
    };

    public static final Feature OCEAN_MONUMENT = new Feature(10387313, 32) {
        @Override
        public boolean test(ChunkRandom rand, int x, int z) {
            return (rand.nextInt(27) + rand.nextInt(27)) / 2 == x
                    && (rand.nextInt(27) + rand.nextInt(27)) / 2 == z;
        }
    };

}
