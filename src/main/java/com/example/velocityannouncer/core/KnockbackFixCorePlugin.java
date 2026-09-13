package com.example.velocityannouncer.core;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import java.util.Map;

@IFMLLoadingPlugin.Name("KB Fix")
@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.TransformerExclusions({"com.example.velocityannouncer.core"})
public final class KnockbackFixCorePlugin implements IFMLLoadingPlugin {
    @Override public String[] getASMTransformerClass() { return new String[] { KnockbackFixTransformer.class.getName() }; }
    @Override public String getModContainerClass() { return null; }
    @Override public String getSetupClass() { return null; }
    @Override public void injectData(Map<String, Object> data) { }
    @Override public String getAccessTransformerClass() { return null; }
}
