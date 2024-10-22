package org.zette.worldgen;

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.pipeline.JNoise;

public class WorldNoise {
    private final JNoise height;
    private final JNoise entities;

    public WorldNoise(long seed) {
        height = JNoise.newBuilder()
                .perlin(seed, Interpolation.QUADRATIC, FadeFunction.CUBIC_POLY)
                .scale(1.0 / 64.0)
                .addModifier(v -> (v + 1) / 2.0)
                .clamp(0.0, 1.0)
                .build();

        entities = JNoise.newBuilder()
                .white(seed)
                .addModifier(v -> (v + 1) / 2.0)
                .build();
    }

    public double getHeight(double x, double y) {
        return height.evaluateNoise(x, y);
    }
    public double getEntity(double x, double y) {
        return entities.evaluateNoise(x, y);
    }
}
