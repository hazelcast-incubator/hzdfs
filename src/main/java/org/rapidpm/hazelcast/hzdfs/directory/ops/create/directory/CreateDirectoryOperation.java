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

package org.rapidpm.hazelcast.hzdfs.directory.ops.create.directory;

import com.hazelcast.nio.Address;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.Operation;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.base.ops.HZDFSBaseOperation;
import org.rapidpm.hazelcast.hzdfs.directory.HZDirectoryRemoteService;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainer;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainerValue;

import java.io.IOException;
import java.util.Optional;

public class CreateDirectoryOperation extends HZDFSBaseOperation {


  private String input;
  private Optional<HZDirectory> returnValue;

  public CreateDirectoryOperation(final String objectName, final String input) {
    super(objectName);
    this.input = input;
  }

  @Override
  public Operation getBackupOperation() {
    return null;
  }

  @Override
  public void run() throws Exception {
    final Address thisAddress = getNodeEngine().getThisAddress();
    System.out.println("Executing "
        + objectName + CreateDirectoryOperation.class.getSimpleName() + ".run( ) on: "
        + thisAddress);

    final HZDirectoryRemoteService hzDirectoryRemoteService = getService();
    final int partitionId = getPartitionId();
    final PartitionContainer partitionContainer = hzDirectoryRemoteService.getPartitionContainer(partitionId);
    final Optional<PartitionContainerValue> containerValue = partitionContainer.getPartitionContainerValue(objectName);

    if (containerValue.isPresent()) {
      final PartitionContainerValue value = containerValue.get();
      final Optional<HZDirectory> hzDirectory = value.addNewRootDirectory(input);
      returnValue = hzDirectory;
      partitionContainer.setPartitionContainerValue(objectName, value);
    }
  }

  @Override
  public boolean returnsResponse() {
    return true;
  }

  @Override
  public Optional<HZDirectory> getResponse() {
    return returnValue;
  }

  @Override
  protected void writeInternal(final ObjectDataOutput out) throws IOException {
    super.writeInternal(out);
    out.writeUTF(input);
  }

  @Override
  protected void readInternal(final ObjectDataInput in) throws IOException {
    super.readInternal(in);
    input = in.readUTF();
  }


}
