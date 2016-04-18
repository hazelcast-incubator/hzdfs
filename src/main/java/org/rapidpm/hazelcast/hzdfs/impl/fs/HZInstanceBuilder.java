/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.rapidpm.hazelcast.hzdfs.impl.fs;

import com.hazelcast.config.Config;
import com.hazelcast.config.ServiceConfig;
import com.hazelcast.config.ServicesConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.jetbrains.annotations.NotNull;
import org.rapidpm.hazelcast.hzdfs.directory.HZDirectoryRemoteService;

import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.HZDFS_DEFAULT_FS;

public class HZInstanceBuilder {


  public HazelcastInstance createHZInstance() {
    return createHZInstance(HZDFS_DEFAULT_FS);
  }

  public HazelcastInstance createHZInstance(final String fsName) {
    final Config config = new Config()
        .setInstanceName(fsName);
    final ServicesConfig servicesConfig = new ServicesConfig();

    servicesConfig.addServiceConfig(getServiceConfigDistributedHZDirectoryRemoteService());

    config.setServicesConfig(servicesConfig);


    return Hazelcast.getOrCreateHazelcastInstance(config);
  }

  @NotNull
  private static ServiceConfig getServiceConfigDistributedHZDirectoryRemoteService() {
    final ServiceConfig serviceDistributedBusinessService = new ServiceConfig();
    serviceDistributedBusinessService.setName(HZDirectoryRemoteService.NAME);
    serviceDistributedBusinessService.setClassName(HZDirectoryRemoteService.class.getName());
    serviceDistributedBusinessService.setEnabled(true);
    return serviceDistributedBusinessService;
  }

}
