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

package org.rapidpm.hazelcast.hzdfs.base.model.fstree;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DirectoryNode extends Node implements DataSerializable {

  public List<DirectoryNode> subDirectories = new ArrayList<>();
  public List<FileNode> files = new ArrayList<>();

  public DirectoryNode() {
    type = NodeType.DIRECTORY;
  }

  public void delete() {
    subDirectories.forEach(DirectoryNode::delete);
//    files.forEach(FileNode::delete);
  }


  @Override
  public void writeData(final ObjectDataOutput out) throws IOException {

  }

  @Override
  public void readData(final ObjectDataInput in) throws IOException {

  }


  @Override
  public String toString() {
    return "DirectoryNode{" +
        "files=" + files +
        ", subDirectories=" + subDirectories +
        '}';
  }
}
