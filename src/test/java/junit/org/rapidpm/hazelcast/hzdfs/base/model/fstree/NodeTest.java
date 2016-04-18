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

package junit.org.rapidpm.hazelcast.hzdfs.base.model.fstree;

import org.junit.Test;
import org.rapidpm.hazelcast.hzdfs.base.model.fstree.DirectoryNode;

public class NodeTest {

  @Test
  public void test001() throws Exception {

    final DirectoryNode root = new DirectoryNode();
    root.name = "/";

    final DirectoryNode lev1 = new DirectoryNode();
    lev1.name = "lev1";
    lev1.parent = root;

    final DirectoryNode lev2a = new DirectoryNode();
    lev2a.name = "lev2a";
    lev2a.parent = lev1;

    final DirectoryNode lev2b = new DirectoryNode();
    lev2b.name = "lev2b";
    lev2b.parent = lev1;

    final DirectoryNode lev3 = new DirectoryNode();
    lev3.name = "lev3";
    lev3.parent = lev2a;

    System.out.println("lev1.absolutePath() = " + lev1.absolutePath());
    System.out.println("lev2.absolutePath() = " + lev2a.absolutePath());
    System.out.println("lev2.absolutePath() = " + lev2b.absolutePath());
    System.out.println("lev2.absolutePath() = " + lev3.absolutePath());


  }
}