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

package org.rapidpm.hazelcast.hzdfs.directory.ops.list.directories;

import com.hazelcast.core.IMap;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.spi.Operation;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.base.model.fstree.DirectoryNode;
import org.rapidpm.hazelcast.hzdfs.base.ops.HZDFSBaseOperation;
import org.rapidpm.hazelcast.hzdfs.directory.container.PartitionContainerValue;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.HZDFS_DIRECTORY_PREFIX;

public class ListDirectoryOperation extends HZDFSBaseOperation {


  private Collection<HZDirectory> returnValue = Collections.emptyList();

  public ListDirectoryOperation(final String objectName) {
    super(objectName);
  }

  @Override
  public Operation getBackupOperation() {
    return null;
  }

  @Override
  public void run() throws Exception {
    final Optional<PartitionContainerValue> containerValue = getPartitionContainerValue();
    if (containerValue.isPresent()) {
      final PartitionContainerValue value = containerValue.get();
      //absoluteParentPath is the directory name
      final Collection<DirectoryNode> allRootDirectories = value.getAllRootDirectories();
      System.out.println("allRootDirectories = " + allRootDirectories);
      final IMap<String, HZDirectory> hzDirectoryIMap = getNodeEngine().getHazelcastInstance().getMap(HZDFS_DIRECTORY_PREFIX + objectName);
      returnValue = hzDirectoryIMap.values();
      // no change
      //partitionContainer.setPartitionContainerValue(objectName, value);
    }
  }

  @Override
  public boolean returnsResponse() {
    return true;
  }

  @Override
  public Collection<HZDirectory> getResponse() {
    return returnValue;
  }

  @Override
  protected void writeInternal(final ObjectDataOutput out) throws IOException {
    super.writeInternal(out);
//    out.writeUTF(objectName);
  }

  @Override
  protected void readInternal(final ObjectDataInput in) throws IOException {
    super.readInternal(in);
//    objectName = in.readUTF();
  }
}
