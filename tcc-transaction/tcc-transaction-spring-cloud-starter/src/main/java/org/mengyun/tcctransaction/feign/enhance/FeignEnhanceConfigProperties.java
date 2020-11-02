package org.mengyun.tcctransaction.feign.enhance;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tcc.feign.enhance")
public class FeignEnhanceConfigProperties {
    /**
     * enable feign enhance.
     */
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}