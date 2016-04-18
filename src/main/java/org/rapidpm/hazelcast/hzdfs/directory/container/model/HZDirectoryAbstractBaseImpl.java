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

package org.rapidpm.hazelcast.hzdfs.directory.container.model;

import com.hazelcast.core.HazelcastInstance;
import org.rapidpm.hazelcast.hzdfs.base.api.HZDirectory;
import org.rapidpm.hazelcast.hzdfs.base.api.HZFSNode;
import org.rapidpm.hazelcast.hzdfs.base.api.HZFile;
import org.rapidpm.hazelcast.hzdfs.base.model.HZFSAbstractNode;
import org.rapidpm.hazelcast.hzdfs.impl.fs.HZInstanceBuilder;

import java.util.Collection;
import java.util.Map;

import static org.rapidpm.hazelcast.hzdfs.impl.HZDFSConstants.*;

abstract class HZDirectoryAbstractBaseImpl extends HZFSAbstractNode implements HZDirectory {

  private final HazelcastInstance hz = new HZInstanceBuilder().createHZInstance();
  protected HZDirectory parent;
  protected String path;
  protected String name;
  //absolute directorypath
  private final Map<String, HZDirectory> directories = hz.getMap(HZDFS_DIRLIST_PREFIX + absolutePath());
  private final Map<String, HZFile> files = hz.getMap(HZDFS_FILE_PREFIX + absolutePath());
  private final Map<String, HZFile> filesRAW = hz.getMap(HZDFS_FILE_RAW_PREFIX + absolutePath());

  HZDirectoryAbstractBaseImpl(final HZDirectory parent, final String name) {
    this.parent = parent;
    this.path = (parent != null) ? parent.absolutePath() : "/";
    this.name = name;
  }

  public HZDirectoryAbstractBaseImpl() {
  }

  public HZDirectory getParent() {
    return parent;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String path() {
    return path;
  }

  @Override
  public String absolutePath() {
    return path + name;
  }

  @Override
  public Collection<HZFile> listFiles() {
    return files.values();
  }

  @Override
  public Collection<HZDirectory> listDirectories() {
    return directories.values();
  }

  @Override
  public Collection<HZDirectory> listDirectories(final String startPath) {
    return null;
  }

  @Override
  public void addDirectory(final HZDirectory newDirectory) {
    directories.put(newDirectory.name(), newDirectory); //TODO check hashCode for distribution
  }

  @Override
  public void delete() {
    directories.values().forEach(HZFSNode::delete);
    files.values().forEach(HZFile::delete);
  }

  @Override
  public void getAttributes() {

  }

  @Override
  public void lock() {

  }

  @Override
  public void unLock() {

  }

  @Override
  public void persist() {

  }

  @Override
  public void unPersist() {

  }
}
