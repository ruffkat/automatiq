package io.webdevice.device;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static io.webdevice.support.ProtectedCapabilities.mask;
import static java.util.Collections.unmodifiableSet;

public abstract class BaseDeviceProvider<Driver extends WebDriver>
        implements DeviceProvider<Driver> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected final Set<String> confidential = new LinkedHashSet<>();
    protected final String name;

    protected Capabilities capabilities;

    protected BaseDeviceProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public Set<String> getConfidential() {
        return unmodifiableSet(confidential);
    }

    public void setConfidential(Set<String> confidential) {
        this.confidential.clear();
        this.confidential.addAll(confidential);
    }

    @Override
    public void dispose() {
        log.info("Provider {} shut down.", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseDeviceProvider<Driver> that = (BaseDeviceProvider<Driver>) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(capabilities, that.capabilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capabilities);
    }

    protected String masked() {
        return mask(capabilities, confidential);
    }
}
