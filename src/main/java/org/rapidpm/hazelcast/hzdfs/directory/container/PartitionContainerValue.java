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
import org.rapidpm.hazelcast.hzdfs.directory.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.file.api.HZFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PartitionContainerValue implements DataSerializable {


  private final List<HZDirectory> directories = new ArrayList<>();
  private final List<HZFile> files = new ArrayList<>();


  @Override
  public void writeData(final ObjectDataOutput out) throws IOException {

  }

  @Override
  public void readData(final ObjectDataInput in) throws IOException {

  }
}
