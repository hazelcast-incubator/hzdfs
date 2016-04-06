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

package org.rapidpm.hazelcast.hzdfs.file;

import com.hazelcast.core.ILock;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import org.rapidpm.hazelcast.hzdfs.file.api.HZFile;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class HZFileDefault implements HZFile {

  private String uuid;
  private ILock lock;

  private int userID;
  private int groupID;

  private boolean isReadonly;
  private boolean isPersistent;

  private byte[] data;

  public HZFileDefault() {
  }

  private HZFileDefault(final Builder builder) {
    data = builder.data;
    uuid = builder.uuid;
    lock = builder.lock;
    userID = builder.userID;
    groupID = builder.groupID;
    isReadonly = builder.isReadonly;
    isPersistent = builder.isPersistent;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static Builder newBuilder(@Nonnull final HZFileDefault copy) {
    Builder builder = new Builder();
    builder.data = copy.data;
    builder.uuid = copy.uuid;
    builder.lock = copy.lock;
    builder.userID = copy.userID;
    builder.groupID = copy.groupID;
    builder.isReadonly = copy.isReadonly;
    builder.isPersistent = copy.isPersistent;
    return builder;
  }


  @Override
  public void writeFileData(final byte[] fileData) {
    this.data = fileData;
  }

  @Override
  public byte[] readFileData() {
    return data;
  }

  @Override
  public String nodeID() {
    return uuid;
  }

  @Override
  public void delete() {
    //
  }

  @Override
  public void getAttributes() {
    //
  }

  @Override
  public boolean isReadonly() {
    return isReadonly;
  }

  @Override
  public boolean isWritable() {
    return !isReadonly;
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
  public void lock() {
    this.lock.lock();
  }

  @Override
  public void unLock() {
    this.lock.unlock();
  }

  @Override
  public void persist() {
    this.isPersistent = true;
  }

  @Override
  public void unPersist() {
    this.isPersistent = false;
  }

  @Override
  public boolean isPersistent() {
    return isPersistent;
  }

  @Override
  public boolean isTransient() {
    return !isPersistent;
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, userID, groupID, data);
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (!(o instanceof HZFileDefault)) return false;
    final HZFileDefault that = (HZFileDefault) o;
    return userID == that.userID &&
        groupID == that.groupID &&
        Objects.equals(uuid, that.uuid) &&
        Arrays.equals(data, that.data);
  }

  @Override
  public String toString() {
    return "HZFileDefault{" +
        "data=" + Arrays.toString(data) +
        ", groupID=" + groupID +
        ", isPersistent=" + isPersistent +
        ", isReadonly=" + isReadonly +
        ", lock=" + lock +
        ", userID=" + userID +
        ", uuid=" + uuid +
        '}';
  }

  //@Override
  public void writeData(final ObjectDataOutput out) throws IOException {
    out.writeUTF(this.uuid);
    out.writeBoolean(this.isPersistent);
    out.writeBoolean(this.isReadonly);
    out.writeInt(this.userID);
    out.writeInt(this.groupID);
    out.writeByteArray(data);
  }

  //@Override
  public void readData(final ObjectDataInput in) throws IOException {
    this.uuid = in.readUTF();
    this.isPersistent = in.readBoolean();
    this.isReadonly = in.readBoolean();
    this.userID = in.readInt();
    this.groupID = in.readInt();
    this.data = in.readByteArray();
  }

  @Override
  public String getPartitionKey() {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public String getServiceName() {
    return null;
  }

  @Override
  public void destroy() {

  }

  public static final class Builder {
    private byte[] data;
    private String uuid;
    private ILock lock;
    private int userID;
    private int groupID;
    private boolean isReadonly;
    private boolean isPersistent;

    private Builder() {
    }

    @Nonnull
    public Builder withData(@Nonnull final byte[] data) {
      this.data = data;
      return this;
    }

    @Nonnull
    public Builder withUuid(@Nonnull final String uuid) {
      this.uuid = uuid;
      return this;
    }

    @Nonnull
    public Builder withNewUuid() {
      this.uuid = UUID.randomUUID().toString();
      return this;
    }


    @Nonnull
    public Builder withLock(@Nonnull final ILock lock) {
      this.lock = lock;
      return this;
    }

    @Nonnull
    public Builder withUserID(final int userID) {
      this.userID = userID;
      return this;
    }

    @Nonnull
    public Builder withGroupID(final int groupID) {
      this.groupID = groupID;
      return this;
    }

    @Nonnull
    public Builder withIsReadonly(final boolean isReadonly) {
      this.isReadonly = isReadonly;
      return this;
    }

    @Nonnull
    public Builder withIsPersistent(final boolean isPersistent) {
      this.isPersistent = isPersistent;
      return this;
    }

    @Nonnull
    public HZFileDefault build() {


      return new HZFileDefault(this);
    }
  }
}
