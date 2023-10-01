package com.sindercube.blastbox.sound;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;


public class BlastboxSoundInstance extends MovingSoundInstance {
    private boolean active;
    private int keepAlive;
    protected BlastboxSoundInstance(SoundEvent sound, SoundCategory category, Random random) {
        super(sound, category, random);
        this.active = true;
        this.keepAlive = 200;
    }
    public BlastboxSoundInstance(SoundEvent sound, float pitch) {
        this(sound, SoundCategory.RECORDS, Random.create());
        this.pitch = pitch;
    }
    @Override
    public void tick() {
        System.out.println("test");
        if (active) {
            volume = Math.min(1, volume + .25f);
            keepAlive--;
            if (keepAlive == 0) fadeOut();
        }
//        volume = Math.max(0, volume - .25f);
//        if (volume == 0) stop();
    }
    public void stop() {
        this.setDone();
    }
    public void fadeOut() {
        this.active = false;
    }
}
