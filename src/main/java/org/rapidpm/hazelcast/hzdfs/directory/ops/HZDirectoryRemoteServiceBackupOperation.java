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

package org.rapidpm.hazelcast.hzdfs.directory.ops;

import com.hazelcast.nio.Address;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.AbstractOperation;
import com.hazelcast.spi.BackupOperation;
import org.rapidpm.hazelcast.hzdfs.directory.HZDirectoryRemoteService;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainer;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainerValue;

import java.io.IOException;
import java.util.Optional;

public class HZDirectoryRemoteServiceBackupOperation extends AbstractOperation implements BackupOperation {

  private String objectName;
  private String input;

  public HZDirectoryRemoteServiceBackupOperation(final String objectName, final String input) {
    this.objectName = objectName;
    this.input = input;
    System.out.println("HZDirectoryRemoteServiceBackupOperation - objectName = " + objectName + " -  input " + input);
  }

  public HZDirectoryRemoteServiceBackupOperation() {
    System.out.println("HZDirectoryRemoteServiceBackupOperation - objectName = " + objectName + " -  input " + input);

  }

  @Override
  protected void writeInternal(final ObjectDataOutput out) throws IOException {
    super.writeInternal(out);
    out.writeUTF(objectName);
    out.writeUTF(input);
  }

  @Override
  protected void readInternal(final ObjectDataInput in) throws IOException {
    super.readInternal(in);
    objectName = in.readUTF();
    input = in.readUTF();
  }

  @Override
  public void run() throws Exception {
    final Address thisAddress = getNodeEngine().getThisAddress();
    System.out.println("Executing Backup "
        + objectName + ".doWork( input = " + input + ") on: "
        + thisAddress);
    final HZDirectoryRemoteService businessService = getService();
    final int partitionId = getPartitionId();
    final PartitionContainer partitionContainer = businessService.getPartitionContainer(partitionId);
    final Optional<PartitionContainerValue> partitionContainerValue = partitionContainer.getPartitionContainerValue(objectName);
//    final String nextValue = lastValueFor + " - B-" + input + thisAddress.getPort();
//    partitionContainer.setlastValueFor(objectName, nextValue);
  }


}
