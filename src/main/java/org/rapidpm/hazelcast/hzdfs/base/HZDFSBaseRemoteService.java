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

package org.rapidpm.hazelcast.hzdfs.base;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.partition.MigrationEndpoint;
import com.hazelcast.spi.*;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainer;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainerMigrationOperation;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainerValue;

import java.util.Map;
import java.util.Properties;

public abstract class HZDFSBaseRemoteService implements ManagedService, RemoteService, MigrationAwareService {

  private NodeEngine nodeEngine;
  private PartitionContainer[] partitionContainers;


  public PartitionContainer getPartitionContainer(int partitionID) {
    return partitionContainers[partitionID];
  }

  //  Managed Service

  @Override
  public void init(final NodeEngine nodeEngine, final Properties properties) {
    System.out.println(this.getClass().getSimpleName() + ".init ");
    this.nodeEngine = nodeEngine;

    partitionContainers = new PartitionContainer[nodeEngine.getPartitionService().getPartitionCount()];
    for (int i = 0; i < partitionContainers.length; i++) {
      partitionContainers[i] = new PartitionContainer();
    }
  }

  @Override
  public void reset() {

  }

  @Override
  public void shutdown(final boolean terminate) {
    System.out.println(this.getClass().getSimpleName() + ".shutdown " + nodeEngine.getThisAddress());
  }


  //  Remote service
  @Override
  public DistributedObject createDistributedObject(final String objectName) {
    int partitionId = nodeEngine.getPartitionService().getPartitionId(objectName);
    final PartitionContainer container = partitionContainers[partitionId];
    container.init(objectName);
    return createProxy(nodeEngine, objectName);
  }

  protected abstract DistributedObject createProxy(NodeEngine nodeEngine, String objectName);

  @Override
  public void destroyDistributedObject(final String objectName) {
    int partitionId = nodeEngine.getPartitionService().getPartitionId(objectName);
    PartitionContainer container = partitionContainers[partitionId];
    container.destroy(objectName);
  }


  //MigrationAwareService
  @Override
  public Operation prepareReplicationOperation(final PartitionReplicationEvent event) {
    final PartitionContainer partitionContainer = partitionContainers[event.getPartitionId()];
    final Map<String, PartitionContainerValue> migrationData = partitionContainer.toMigrationData();
    return new PartitionContainerMigrationOperation(migrationData);
  }

  @Override
  public void beforeMigration(final PartitionMigrationEvent event) {

  }

  @Override // clean Source
  public void commitMigration(final PartitionMigrationEvent event) {
    if (event.getMigrationEndpoint() == MigrationEndpoint.SOURCE) {
      final PartitionContainer partitionContainer = partitionContainers[event.getPartitionId()];
      partitionContainer.clear();
    }
  }

  @Override // clean Destination
  public void rollbackMigration(final PartitionMigrationEvent event) {
    if (event.getMigrationEndpoint() == MigrationEndpoint.DESTINATION) {
      final PartitionContainer partitionContainer = partitionContainers[event.getPartitionId()];
      partitionContainer.clear();
    }
  }

  @Override
  public void clearPartitionReplica(final int partitionId) {
    partitionContainers[partitionId].clear();
  }

}
