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

package org.rapidpm.hazelcast.hzdfs.base.model;

import org.rapidpm.hazelcast.hzdfs.base.api.HZFSNode;

import java.util.UUID;

public abstract class HZFSAbstractNode implements HZFSNode {

  private final String nodeID = UUID.randomUUID().toString();
  private boolean readOnly;
  private boolean persistent;
  private int groupID;
  private int userID;

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(final boolean readOnly) {
    this.readOnly = readOnly;
  }

  public void setGroupID(final int groupID) {
    this.groupID = groupID;
  }

  public void setUserID(final int userID) {
    this.userID = userID;
  }

  @Override
  public String nodeID() {
    return nodeID;
  }

  @Override
  public boolean isReadonly() {
    return readOnly;
  }

  @Override
  public boolean isWritable() {
    return !readOnly;
  }

  @Override
  public int groupID() {
    return groupID;
  }

  @Override
  public int userID() {
    return userID;
  }

  @Override
  public boolean isPersistent() {
    return persistent;
  }

  public void setPersistent(final boolean persistent) {
    this.persistent = persistent;
  }

  @Override
  public boolean isTransient() {
    return false;
  }
}
