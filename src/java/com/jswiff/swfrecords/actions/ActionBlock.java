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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * This class implements a container for action records. It is used in actions
 * which contain other actions (e.g. <code>DefineFunction</code> or
 * <code>With</code>).
 * </p>
 * 
 * <p>
 * Build nested action blocks bottom-up, i.e. the inner blocks first. For
 * example if you have a <code>With</code>action inside a
 * <code>DefineFunction2</code> action block, first add actions to the action
 * block of <code>With</code>, then add <code>With</code> to the action block
 * of <code>DefineFunction2</code>. Finally, add <code>DefineFunction2</code>
 * to the top level action block.
 * </p>
 */
public final class ActionBlock implements Serializable {

  private static final long serialVersionUID = 1L;
  
  /** Label name pointing to the end of the current action block. */
  public static String LABEL_END = "__end";
  /**
   * Label name pointing outside the block (usually an error). Use this only
   * for error checking!
   */
  public static String LABEL_OUT = "__out";
  private static int instCounter = 0; // instance counter used for labels
  private List<Action> actions                 = new ArrayList<Action>();
  private Map<String, Object> labelMap         = new HashMap<String, Object>();
  private Map<Integer, String> inverseLabelMap = new HashMap<Integer, String>();

  /**
   * Creates a new Block action.
   */
  public ActionBlock() {
    // nothing to do
  }

  /**
   * Reads an action block from a bit stream.
   *
   * @param stream the source bit stream
   *
   * @throws IOException if an I/O error has occured
   */
  public ActionBlock(InputBitStream stream) throws IOException {
    int startOffset      = (int) stream.getOffset();
    boolean hasEndAction = false;
    while (stream.available() > 0) {
      Action record = ActionReader.readRecord(stream);
      if (!ActionType.END.equals(record.actionType())) {
        actions.add(record);
      } else {
        hasEndAction = true;
        break;
      }
    }
    if (actions.size() == 0) {
      return;
    }

    // end offset (relative to start offset, end action ignored)
    int relativeEndOffset = (int) stream.getOffset() - startOffset -
      (hasEndAction ? 1 : 0);

    // correct offsets, setting to relative to first action (not to start of stream)
    // also, populate the label map with integers containing the corresponding offsets
    int labelCounter = 0;
    Map<Integer, Action> actionMap = new HashMap<Integer, Action>(); // contains  offset->action  mapping
    for (Action action : actions) {
      int newOffset = action.getOffset() - startOffset;
      action.setOffset(newOffset);
      actionMap.put(new Integer(newOffset), action);
      // collect labels from Jump and If actions
      if ( (ActionType.IF.equals(action.actionType())) ||
           (ActionType.JUMP.equals(action.actionType())) ) {
        Branch branchAction = (Branch) action;

        // temporarily put the offset into the label map
        // later on, the offset will be replaced with the corresponding action instance
        int branchOffset = getBranchOffset(branchAction);
        String branchLabel;
        if (branchOffset < 0) {
          branchLabel = LABEL_OUT;
        } else if (branchOffset < relativeEndOffset) {
          // check if branch target isn't already assigned a label
          String oldLabel = inverseLabelMap.get(branchOffset);
          if (oldLabel == null) {
            branchLabel = "L_" + instCounter + "_" + labelCounter++;
            labelMap.put(branchLabel, branchOffset);
            inverseLabelMap.put(branchOffset, branchLabel);
          } else {
            branchLabel = oldLabel;
          }
        } else if (branchOffset == relativeEndOffset) {
          branchLabel = LABEL_END;
        } else {
          branchLabel = LABEL_OUT;
        }
        branchAction.setBranchLabel(branchLabel);
      }
    }

    // now replace offsets from label map with corresponding actions
    for (String label : labelMap.keySet()) {
      Object branchOffset = labelMap.get(label);
      Action action       = actionMap.get(branchOffset);
      if (action != null) {
        // action == null when label == LABEL_OUT
        action.setLabel(label);
        labelMap.put(label, action);
      }
    }
    instCounter++;
  }

  /**
   * Resets the instance counter. This counter is used to create action labels
   * when parsing an SWF file.
   */
  public static void resetInstanceCounter() {
    instCounter = 0;
  }

  /**
   * Returns a list of the contained action records. Warning: use this list in
   * a read-only manner!
   *
   * @return contained actions in a list
   */
  public List<Action> getActions() {
    return actions;
  }

  /**
   * Returns the size of the action block in bytes, i.e. the sum of the size of
   * the contained action records.
   *
   * @return size of block in bytes
   */
  public int getSize() {
    int size = 0;
    for (Action action : actions) {
      size += action.getSize();
    }
    return size;
  }

  /**
   * Adds an action record to this action block.
   *
   * @param action an action record
   */
  public void addAction(Action action) {
    // add action to list
    actions.add(action);
  }

  /**
   * Removes the specified action record from the action block.
   *
   * @param action action record to be removed
   *
   * @return <code>true</code> if action block contained the specified action
   *         record
   */
  public boolean removeAction(Action action) {
    return actions.remove(action);
  }

  /**
   * Removes the action record at the specified position within the block.
   *
   * @param index index of the action record to be removed
   *
   * @return the action record previously contained at specified position
   */
  public Action removeAction(int index) {
    return actions.remove(index);
  }

  /**
   * Writes the action block to a bit stream.
   *
   * @param stream the target bit stream
   * @param writeEndAction if <code>true</code>, an END action is written at
   *        the end of the block
   *
   * @throws IOException if an I/O error has occured
   */
  public void write(OutputBitStream stream, boolean writeEndAction)
    throws IOException {
    // two passes
    // first pass: correct offsets and populate labelMap
    int currentOffset = 0;
    for (Action action : actions) {
      action.setOffset(currentOffset);
      currentOffset += action.getSize();
      // if action has label, add (label->action) mapping to labelMap
      String label = action.getLabel();
      if (label != null) {
        labelMap.put(label, action);
      }
    }

    // second pass: replace branch labels with branch offsets and write actions
    for (Action action : actions) {
      switch (action.actionType()) {
        case JUMP:
        case IF:
          Branch branchAction = (Branch) action;
          if (!LABEL_OUT.equals(branchAction.getBranchLabel())) {
            replaceBranchLabelWithRelOffset(branchAction); // replace branch label with offset relative to subsequent action
          }
          break;
      }
      action.write(stream);
    }

    // now write END action if needed
    if (writeEndAction) {
      stream.writeUI8((short) 0); // ActionEndFlag
    }
  }

  /*
   * Returns the action corresponding to a specific label. Labels are used for
   * jumps within this action block.
   */
  private Action getAction(String label) {
    Object action = labelMap.get(label);
    if (action instanceof Action) {
      return (Action) action;
    }
    throw new IllegalArgumentException(
      "Label '" + label + "' points at non-existent action!");
  }
  
  /*
   * Returns the absolute branch offset of an action.
   */
  private int getBranchOffset(Branch action) {
    int branchOffset = 0;
    branchOffset = action.getBranchOffset();
    // convert from relative to absolute offset 
    branchOffset += (action.getOffset() + action.getSize());
    return branchOffset;
  }

  /*
   * Returns the absolute offset corresponding to a given label.
   */
  private int getOffset(String label) {
    if (label.equals(LABEL_END)) {
      return getSize();
    }
    Action action = getAction(label);
    if (action == null) {
      throw new IllegalArgumentException("Label " + label + " not defined!");
    }
    return action.getOffset();
  }

  private void replaceBranchLabelWithRelOffset(Branch action) {
    // replace branch label with offset relative to subsequent action
    // get absolute offset corresponding to branch label
    short branchOffset = (short) getOffset(action.getBranchLabel());

    // compute offset relative to subsequent action
    branchOffset -= (action.getOffset() + action.getSize());
    // set branch offset
    action.setBranchOffset(branchOffset);
  }
}
