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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.directory.container.model.HZDirectoryRootImpl;
import org.rapidpm.hazelcast.hzdfs.impl.fs.HZInstanceBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.HZDFS_DIRECTORY_PREFIX;


// All Informations of a Root-Directory and all childs
public class PartitionContainerValue implements DataSerializable {

  private final String objectName; // Name of the root Directory

  private final transient HazelcastInstance hz;
  private final transient Map<String, HZDirectory> directories;

  public PartitionContainerValue(final String objectName) {
    this.objectName = objectName;
    hz = new HZInstanceBuilder().createHZInstance(); //TODO more dynamic
    directories = hz.getMap(HZDFS_DIRECTORY_PREFIX + objectName);
  }

  // key -> parent directory name

  public Collection<HZDirectory> getAllDirectories() {
    return directories.values();
  }

  public Optional<HZDirectory> addNewRootDirectory(final String directoryName) {
    if (directoryName == null || directoryName.isEmpty()) {
      return Optional.empty();
    }

    final HZDirectory hzDirectory = directories.get(directoryName);
    if (hzDirectory != null) { //TODO ???
      return Optional.of(hzDirectory);
    } else {
      final HZDirectory newDirectory = new HZDirectoryRootImpl(directoryName);
      directories.put(newDirectory.name(), newDirectory);
      return Optional.of(newDirectory);
    }
  }


  public void updateDirectory(final HZDirectory directory2Update) {
    if (directory2Update != null) {
      directories.put(directory2Update.name(), directory2Update);
    } else {
      //nothing
    }
  }


  public boolean deleteDirectory(final String absolutePath) {
    if (absolutePath == null || absolutePath.isEmpty()) return false;

    final boolean containsKey = directories.containsKey(absolutePath);
    if (containsKey) {
      final HZDirectory hzDirectory = directories.get(absolutePath);
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
}
