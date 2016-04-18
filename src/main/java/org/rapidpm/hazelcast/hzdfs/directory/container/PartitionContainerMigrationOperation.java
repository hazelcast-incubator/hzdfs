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

package org.rapidpm.hazelcast.hzdfs.directory.container;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.AbstractOperation;
import org.rapidpm.hazelcast.hzdfs.directory.HZDirectoryRemoteService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PartitionContainerMigrationOperation extends AbstractOperation {

  private Map<String, PartitionContainerValue> migrationData;

  public PartitionContainerMigrationOperation() {
  }

  public PartitionContainerMigrationOperation(final Map<String, PartitionContainerValue> migrationData) {
    System.out.println("migrationData = " + migrationData);
    this.migrationData = migrationData;
  }

  @Override
  public void run() throws Exception {
    final HZDirectoryRemoteService service = getService();
    final PartitionContainer businesServicePartitionContainer = service.getPartitionContainer(getPartitionId());
    businesServicePartitionContainer.applyMigrationData(migrationData);
  }


  @Override
  protected void writeInternal(final ObjectDataOutput out) throws IOException {

    out.writeInt(migrationData.size());
    for (final Entry<String, PartitionContainerValue> entry : migrationData.entrySet()) {
      out.writeUTF(entry.getKey());
      entry.getValue().writeData(out);
    }
  }

  @Override
  protected void readInternal(final ObjectDataInput in) throws IOException {
    int size = in.readInt();
    migrationData = new HashMap<>();
    for (int i = 0; i < size; i++) {
      final String key = in.readUTF();
      final PartitionContainerValue partitionContainerValue = new PartitionContainerValue(key);
      partitionContainerValue.readData(in);
      migrationData.put(key, partitionContainerValue);
    }
  }

}
