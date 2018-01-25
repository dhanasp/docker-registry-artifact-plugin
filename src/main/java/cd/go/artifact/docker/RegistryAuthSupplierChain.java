/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cd.go.artifact.docker;

import cd.go.artifact.docker.model.ArtifactStoreConfig;
import com.spotify.docker.client.auth.RegistryAuthSupplier;
import com.spotify.docker.client.messages.RegistryAuth;
import com.spotify.docker.client.messages.RegistryConfigs;

import java.util.Collections;

public class RegistryAuthSupplierChain implements RegistryAuthSupplier {
    private final RegistryAuth registryAuth;

    public RegistryAuthSupplierChain(ArtifactStoreConfig artifactStoreConfig) {
        registryAuth = RegistryAuth.builder()
                .username(artifactStoreConfig.getUsername())
                .serverAddress(artifactStoreConfig.getRegistryUrl())
                .password(artifactStoreConfig.getPassword()).build();
    }

    @Override
    public RegistryAuth authFor(String imageName) {
        return registryAuth;
    }

    @Override
    public RegistryAuth authForSwarm() {
        return registryAuth;
    }

    @Override
    public RegistryConfigs authForBuild() {
        return RegistryConfigs.create(Collections.singletonMap(registryAuth.serverAddress(), registryAuth));
    }
}
