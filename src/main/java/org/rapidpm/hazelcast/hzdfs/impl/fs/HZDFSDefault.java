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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spi.NodeEngine;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDFS;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.base.api.HZFile;
import org.rapidpm.hazelcast.hzdfs.directory.HZDirectoryRemoteServiceProxy;
import org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants;

import java.util.Optional;
import java.util.Properties;

import static org.rapidpm.hazelcast.hzdfs.directory.HZDirectoryRemoteService.NAME;
import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.HZDFS_DISTRIBUTED_OBJECT_PREFIX;

public class HZDFSDefault implements HZDFS {

  private final HazelcastInstance hz;
  private String name = HZDFSConstants.HZDFS_DEFAULT_FS;
  private NodeEngine nodeEngine;

  HZDFSDefault(final String name, final HazelcastInstance hz) {
    this.name = name;
    this.hz = hz;
  }

  @Override
  public String name() {
    return null;
  }

  @Override
  public HZFile createNewHZFile() {
    return null;
  }

  @Override
  public void updateHZFile(final HZFile hzFile) {

  }

  @Override
  public String getGroup(final int groupID) {
    return null;
  }

  @Override
  public String getUser(final int userID) {
    return null;
  }

  @Override
  public int getGroudID(final String groupName) {
    return 0;
  }

  @Override
  public int getUserID(final String userName) {
    return 0;
  }

  @Override
  public Optional<HZDirectory> getRootDirectory(final String rootName) {
    //create a dir instance with the rootName
    final HZDirectoryRemoteServiceProxy proxy = hz.getDistributedObject(NAME, HZDFS_DISTRIBUTED_OBJECT_PREFIX + rootName);
    return Optional.ofNullable(proxy);
  }

  @Override
  public void init(final NodeEngine nodeEngine, final Properties properties) {
    this.nodeEngine = nodeEngine;

  }

  @Override
  public void reset() {

  }

  @Override
  public void shutdown(final boolean terminate) {

  }
}
