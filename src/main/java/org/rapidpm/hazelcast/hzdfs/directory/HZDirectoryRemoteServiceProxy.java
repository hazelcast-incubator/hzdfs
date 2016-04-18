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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.spi.AbstractDistributedObject;
import com.hazelcast.spi.InvocationBuilder;
import com.hazelcast.spi.NodeEngine;
import com.hazelcast.util.ExceptionUtil;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.base.api.HZFile;
import org.rapidpm.hazelcast.hzdfs.directory.ops.create.directory.CreateRootDirectoryOperation;
import org.rapidpm.hazelcast.hzdfs.directory.ops.list.directories.ListDirectoryOperation;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.Future;

import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.HZDFS_DIRECTORY_PREFIX;

public class HZDirectoryRemoteServiceProxy
    extends AbstractDistributedObject<HZDirectoryRemoteService>
    implements HZDirectory {


  private final String objectName;

  public HZDirectoryRemoteServiceProxy(final NodeEngine nodeEngine,
                                       final HZDirectoryRemoteService service,
                                       final String objectName) {
    super(nodeEngine, service);
    this.objectName = objectName;
  }

  @Override
  public String getName() {
    return objectName;
  }

  @Override
  public String getServiceName() {
    return HZDirectoryRemoteService.NAME;
  }

  @Override
  public String name() {

    return null;
  }

  @Override
  public String path() {
    return null;
  }

  @Override
  public String absolutePath() {
    return null;
  }

  @Override
  public Collection<HZFile> listFiles() {
    return Collections.emptyList();
  }

  @Override
  public Collection<HZDirectory> listDirectories() {
    final NodeEngine nodeEngine = getNodeEngine();
    final ListDirectoryOperation operation = new ListDirectoryOperation(objectName);
    final int partitionId = nodeEngine
        .getPartitionService()
        .getPartitionId(objectName);
    final InvocationBuilder builder = nodeEngine
        .getOperationService()
        .createInvocationBuilder(HZDirectoryRemoteService.NAME, operation, partitionId);
    try {
      final Future<Collection<HZDirectory>> future = builder.invoke();

      // get the real object from HZ
      final HazelcastInstance hz = getNodeEngine().getHazelcastInstance();
      final IMap<Object, Object> iMap = hz.getMap("");
//      iMap.get()


      return future.get();
    } catch (Exception e) {
      throw ExceptionUtil.rethrow(e);
    }
  }

  @Override
  public Collection<HZDirectory> listDirectories(final String startPath) {
    return null;
  }

  @Override
  public void addDirectory(final HZDirectory newDirectory) {


    final NodeEngine nodeEngine = getNodeEngine();
    //TODO refactoring
    final CreateRootDirectoryOperation operation = new CreateRootDirectoryOperation(objectName, newDirectory.name());
    final int partitionId = nodeEngine
        .getPartitionService()
        .getPartitionId(objectName);
    final InvocationBuilder builder = nodeEngine
        .getOperationService()
        .createInvocationBuilder(HZDirectoryRemoteService.NAME, operation, partitionId);
    try {
      final Future<Optional<HZDirectory>> future = builder.invoke();
      final Optional<HZDirectory> hzDirectory = future.get();
      System.out.println("hzDirectory = " + hzDirectory);
      if (hzDirectory.isPresent()) {
        final HZDirectory dir = hzDirectory.get();
        final IMap<String, HZDirectory> hzDirectoryIMap = getNodeEngine().getHazelcastInstance().getMap(HZDFS_DIRECTORY_PREFIX + objectName);
        hzDirectoryIMap.put(dir.absolutePath(), dir);
      }
    } catch (Exception e) {
      throw ExceptionUtil.rethrow(e);
    }
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
