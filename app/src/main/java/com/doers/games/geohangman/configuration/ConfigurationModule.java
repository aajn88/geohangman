package com.doers.games.geohangman.configuration;

import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.services.impl.GeoHangmanService;
import com.google.inject.AbstractModule;

/**
 * This module configures Interfaces with their respective implementation
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class ConfigurationModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IGeoHangmanService.class).to(GeoHangmanService.class);
    }

}
