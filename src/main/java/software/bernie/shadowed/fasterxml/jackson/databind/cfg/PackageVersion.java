package software.bernie.shadowed.fasterxml.jackson.databind.cfg;

import software.bernie.shadowed.fasterxml.jackson.core.Version;
import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;

/**
 * Automatically generated from PackageVersion.java.in during
 * packageVersion-generate execution of maven-replacer-plugin in
 * pom.xml.
 */
public final class PackageVersion implements Versioned {
    public final static Version VERSION = VersionUtil.parseVersion(
        "@projectversion@", "@projectgroupid@", "@projectartifactid@");

    @Override
    public Version version() {
        return VERSION;
    }
}