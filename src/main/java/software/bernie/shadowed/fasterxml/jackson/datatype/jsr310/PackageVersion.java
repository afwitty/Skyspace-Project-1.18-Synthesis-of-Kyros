/*
 * Copyright 2013 FasterXML.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310;

import software.bernie.shadowed.fasterxml.jackson.core.Version;
import software.bernie.shadowed.fasterxml.jackson.core.Versioned;
import software.bernie.shadowed.fasterxml.jackson.core.util.VersionUtil;

/**
 * Automatically generated from PackageVersion.java.in during
 * packageVersion-generate execution of maven-replacer-plugin in
 * pom.xml.
 */
public final class PackageVersion implements Versioned
{
    public final static Version VERSION = VersionUtil.parseVersion(
        "@projectversion@", "@projectgroupid@", "@projectartifactid@"
    );

    @Override
    public Version version()
    {
        return VERSION;
    }
}
