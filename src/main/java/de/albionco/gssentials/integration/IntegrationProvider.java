package de.albionco.gssentials.integration;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

public abstract class IntegrationProvider {
    private static HashMap<String, Class<? extends IntegrationProvider>> providers = new HashMap<>();
    private static HashMap<Class<? extends IntegrationProvider>, IntegrationProvider> instances = new HashMap<>();

    static {
        /*
         * If we create new instances of the provider classes and the plugin
         * doesn't exist or hasn't been registered then exceptions are thrown
         */
        providers.put("BungeeAdminTools", AdminToolsProvider.class);
        providers.put("BungeeSuite", BungeeSuiteProvider.class);
    }

    public static IntegrationProvider get(String name) {
        Class<? extends IntegrationProvider> clazz = providers.get(name);
        if (instances.get(clazz) == null) {
            try {
                instances.put(clazz, clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                try {
                    Constructor<? extends IntegrationProvider> providerConstructor = clazz.getDeclaredConstructor();
                    if (!providerConstructor.isAccessible()) {
                        providerConstructor.setAccessible(true);
                    }
                    IntegrationProvider provider = providerConstructor.newInstance();
                    instances.put(clazz, provider);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e1) {
                    // ignored, nothing we can really do at this point
                }
            }
        }
        return instances.get(clazz);
    }

    public static Set<String> getPlugins() {
        return providers.keySet();
    }
    
    public abstract boolean isMuted(ProxiedPlayer player);

    public abstract boolean isEnabled();
    public abstract String getName();
}
