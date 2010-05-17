/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
 */

package com.jswiff.swfrecords.actions;

import com.jswiff.constants.TagConstants.ActionType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;


/**
 * <p>
 * Evaluates a condition to determine the next action in an SWF file. If the
 * condition is true, the execution continues at the action with the specified
 * label.
 * </p>
 * 
 * <p>
 * Performed stack operations:<br>
 * <code>pop cond</code> (the condition - must evaluate to 0 or 1, or as of SWF
 * 5 to <code>false</code> or <code>true</code>)
 * </p>
 * 
 * <p>
 * ActionScript equivalent: <code>if</code> statement
 * </p>
 *
 * @see ActionBlock
 * @since SWF 4
 */
public final class If extends Branch {

  private static final long serialVersionUID = 1L;
  
  private short branchOffset;
  private String branchLabel;

  /**
   * Creates a new If action.<br>
   * The <code>branchLabel</code> parameter specifies the target of the jump
   * in case the condition is fulfilled. This label must be identical to the
   * one assigned to the action record the execution is supposed to continue
   * at. Assign <code>ActionBlock.LABEL_END</code> in order to jump to the end
   * of the action block.
   *
   * @param branchLabel label of the action the execution is supposed to
   *        continue at
   */
  public If(String branchLabel) {
    super(ActionType.IF);
    this.branchLabel   = branchLabel;
  }

  /*
   * Reads an If action from a bit stream.
   */
  If(InputBitStream stream) throws IOException {
    super(ActionType.IF);
    branchOffset   = stream.readSI16();
  }

  /**
   * Returns the label of the action the execution is supposed to continue at,
   * if the condition is fulfilled.
   *
   * @return branch label
   */
  public String getBranchLabel() {
    return branchLabel;
  }

  /**
   * Returns the branch offset.
   *
   * @return branch offset
   */
  public short getBranchOffset() {
    return branchOffset;
  }

  /**
   * Returns the size of this action record in bytes.
   *
   * @return size of this record
   *
   * @see Action#getSize()
   */
  public int getSize() {
    return 5; // 1 (code) + 2 (data length) + 2 (branch offset)
  }

  /**
   * Returns a short description of this action.
   *
   * @return <code>"If", branchLabel</code>
   */
  public String toString() {
    return super.toString() + " branchLabel: " + branchLabel;
  }

  protected void writeData(
    OutputBitStream dataStream, OutputBitStream mainStream)
    throws IOException {
    dataStream.writeSI16(branchOffset);
  }

  void setBranchLabel(String branchLabel) {
    this.branchLabel = branchLabel;
  }

  public void setBranchOffset(short branchOffset) {
    this.branchOffset = branchOffset;
  }
}
