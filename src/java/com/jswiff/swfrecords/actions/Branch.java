/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.swfrecords.actions;

/**
 * Base class for branch actions (<code>Jump</code>, <code>If</code>).
 */
public abstract class Branch extends Action {
  /**
   * Returns the label of the action the execution is supposed to continue at,
   * if the condition is fulfilled.
   *
   * @return branch label
   */
  public abstract String getBranchLabel();

  abstract void setBranchLabel(String branchLabel);

  abstract void setBranchOffset(short branchOffset);

  abstract short getBranchOffset();
}