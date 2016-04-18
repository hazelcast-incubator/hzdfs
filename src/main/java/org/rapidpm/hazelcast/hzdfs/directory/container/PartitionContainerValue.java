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
import com.hazelcast.nio.serialization.DataSerializable;
import org.rapidpm.hazelcast.hzdfs.base.model.fstree.DirectoryNode;

import java.io.IOException;
import java.util.*;


// All Informations of a Root-Directory and all childs
public class PartitionContainerValue implements DataSerializable {

  private final String objectName; // Name of the root Directory

  private final Map<String, DirectoryNode> directoryNodes = new HashMap<>();


  public PartitionContainerValue(final String objectName) {
    this.objectName = objectName;
//    hz = new HZInstanceBuilder().createHZInstance(); //TODO more dynamic
//    directories = hz.getMap(HZDFS_DIRECTORY_PREFIX + objectName);
  }

  public Collection<DirectoryNode> getAllRootDirectories() {
    return directoryNodes.values();
  }

  public Optional<DirectoryNode> addNewRootDirectory(final String directoryName) {
    if (directoryName == null || directoryName.isEmpty()) {
      return Optional.empty();
    }

    if (directoryNodes.containsKey(directoryName)) {
      return Optional.ofNullable(directoryNodes.get(directoryName));
    } else {
      final DirectoryNode directoryNode = new DirectoryNode();
      directoryNode.parent = null;
      directoryNode.name = directoryName;
      directoryNode.nodeID = UUID.randomUUID().toString();
      directoryNodes.put(directoryNode.name, directoryNode);
      return Optional.of(directoryNode);
    }
  }

  public void updateDirectory(final DirectoryNode directory2Update) {
    if (directory2Update != null) {
      directoryNodes.put(directory2Update.name, directory2Update);
    } else {
      //nothing
    }
  }


  public boolean deleteDirectory(final String absolutePath) {
    if (absolutePath == null || absolutePath.isEmpty()) return false;

    final boolean containsKey = directoryNodes.containsKey(absolutePath);
    if (containsKey) {
      final DirectoryNode hzDirectory = directoryNodes.get(absolutePath);
      //rekursiver Abstieg zum delete
      hzDirectory.delete();
    }
    return containsKey;
  }

  @Override
  public void writeData(final ObjectDataOutput out) throws IOException {

  }

  @Override
  public void readData(final ObjectDataInput in) throws IOException {

  }

  // select * where parentNodeUUID = "xxx"; get all childs
  // select * where parentNodeUUID = "xxx"; get all childs

//  public static class HZDirectoryEntry implements DataSerializable {
//
//    private String parentNodeUUID;
//    private String nodeUUID;
//
//    private String absolutePath;
//    private String name;
//    private String path;
//
//
//    @Override
//    public void writeData(final ObjectDataOutput out) throws IOException {
//
//    }
//
//    @Override
//    public void readData(final ObjectDataInput in) throws IOException {
//
//    }
//
//
//  }


}
