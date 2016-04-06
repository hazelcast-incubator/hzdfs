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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PartitionContainer {

  //here we could store a state that will be unique
  // per partition. Only one Thread is running here,
  // so no concurrency problems

  //every identity will have an entry

  private final Map<String, PartitionContainerValue> object2ValueMap = new HashMap<>();


  public void init(String objectName) {
  }

  public void destroy(String objectName) {
    object2ValueMap.remove(objectName);
  }


  public void clear() {
    object2ValueMap.clear();
  }

  void applyMigrationData(final Map<String, PartitionContainerValue> migrationData) {
    object2ValueMap.putAll(migrationData);
  }

  public Map<String, PartitionContainerValue> toMigrationData() {
    return new HashMap<>(object2ValueMap); //TODO iterate over all Values and create Deep Copy ?
  }

  public Optional<PartitionContainerValue> getPartitionContainerValue(String objectName) {
    return Optional.ofNullable(object2ValueMap.get(objectName));
  }

  public void setPartitionContainerValue(String objectName, PartitionContainerValue partitionContainerValue) {
    object2ValueMap.put(objectName, partitionContainerValue);
  }

  @Override
  public String toString() {
    return "PartitionContainer{" +
        "values=" + object2ValueMap +
        '}';
  }
}
