package lgbt.audrey.pipe.plugin;

import lgbt.audrey.pipe.plugin.module.Module;
import lgbt.audrey.pipe.util.Toggleable;

import java.util.List;

/**
 * The base of all plugins. An individual plugin is not meant to be a "mod" or
 * "cheat" or "hack" or <insert term here> on its own; rather, it is meant to
 * have individual {@link Module}s registered
 * to it. Each <tt>Module</tt> is then responsible for being the individual
 * "mod" or "cheat" that a given plugin provides. Each plugin will, by default,
 * provide a "manifest" of modules that it provides, so that an end-user (or a
 * developer) would be able to both acquire information about a given module
 * and also enable or disable individual modules essentially at will.
 *
 * @author c
 * @since 7/10/15
 */
public interface Plugin extends Loadable, Toggleable {
    /**
     * Returns the name of this plugin. May not be null
     *
     * @return The name of this plugin
     */
    String getName();

    /**
     * Returns the author of this plugin. May be null
     *
     * @return The author of this plugin
     */
    String getAuthor();

    /**
     * Returns the description of this plugin. May be null
     *
     * @return The description of this plugin
     */
    String getDescription();

    /**
     * Returns the list of all modules that this plugin provides. May not be
     * null. May be empty.
     *
     * @return The list of all modules that this plugin provides.
     */
    List<Module> getProvidedModules();

    /**
     * Registers a new module for this plugin.
     *
     * @param module The module to register. May not be null.
     */
    void registerModule(Module module);

    /**
     * Unregisters a module from this plugin.
     *
     * @param module The plugin to unregister. May not be null.
     */
    void unregisterModule(Module module);

    /**
     * Returns the {@link PluginManifest} for this plugin. The manifest must
     * contain as much information as a given plugin is able to supply.
     *
     * @return The {@link PluginManifest} for this plugin
     */
    PluginManifest getManifest();

    /**
     * Sets the {@link PluginManifest} for this plugin. This is intended to
     * be called only from {@link PluginManager#init()}. Note that after
     * invocation of this method, it is necessary to call
     * {@link #loadManifestData()} in order to update the stored data.
     *
     * @param manifest The new manifest for the plugin.
     */
    void setManifest(PluginManifest manifest);

    /**
     * Loads data from the {@link PluginManifest}. Intended to only be called
     * once.
     */
    void loadManifestData();
}
