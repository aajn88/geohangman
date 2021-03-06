package com.doers.games.geohangman.configuration;

import com.doers.games.geohangman.managers.IChallengesManager;
import com.doers.games.geohangman.managers.IUsersManager;
import com.doers.games.geohangman.managers.impl.ChallengesManager;
import com.doers.games.geohangman.managers.impl.UsersManager;
import com.doers.games.geohangman.services.IGeoHangmanService;
import com.doers.games.geohangman.services.IServerClientService;
import com.doers.games.geohangman.services.ITokenService;
import com.doers.games.geohangman.services.IUsersService;
import com.doers.games.geohangman.services.impl.GeoHangmanService;
import com.doers.games.geohangman.services.impl.ServerClientService;
import com.doers.games.geohangman.services.impl.TokenService;
import com.doers.games.geohangman.services.impl.UsersService;
import com.google.inject.AbstractModule;

/**
 * This module configures Interfaces with their respective implementation
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class ConfigurationModule extends AbstractModule {

    /**
     * This method binds Services Interfaces with their implementations
     */
    @Override
    protected void configure() {
        bind(IGeoHangmanService.class).to(GeoHangmanService.class);
        bind(IServerClientService.class).to(ServerClientService.class);
        bind(IUsersService.class).to(UsersService.class);
        bind(ITokenService.class).to(TokenService.class);
        bind(IUsersManager.class).to(UsersManager.class);
        bind(IChallengesManager.class).to(ChallengesManager.class);
    }

}
