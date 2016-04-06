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

package org.rapidpm.hazelcast.hzdfs.directory;

import com.hazelcast.spi.AbstractDistributedObject;
import com.hazelcast.spi.NodeEngine;
import org.rapidpm.hazelcast.hzdfs.directory.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.file.api.HZFile;

import java.util.Collection;

public class HZDirectoryRemoteServiceProxy
    extends AbstractDistributedObject<HZDirectoryRemoteService>
    implements HZDirectory {


  private final String name;

  public HZDirectoryRemoteServiceProxy(final NodeEngine nodeEngine, final HZDirectoryRemoteService service, final String name) {
    super(nodeEngine, service);
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getServiceName() {
    return HZDirectoryRemoteService.class.getSimpleName();
  }

  @Override
  public Collection<HZFile> listFiles() {
    return null;
  }

  @Override
  public Collection<HZDirectory> listDirectories() {
    return null;
  }

  @Override
  public String nodeID() {
    return null;
  }

  @Override
  public void delete() {

  }

  @Override
  public void getAttributes() {

  }

  @Override
  public boolean isReadonly() {
    return false;
  }

  @Override
  public boolean isWritable() {
    return false;
  }

  @Override
  public int groupID() {
    return 0;
  }

  @Override
  public int userID() {
    return 0;
  }

  @Override
  public void lock() {

  }

  @Override
  public void unLock() {

  }

  @Override
  public void persist() {

  }

  @Override
  public void unPersist() {

  }

  @Override
  public boolean isPersistent() {
    return false;
  }

  @Override
  public boolean isTransient() {
    return false;
  }

}
