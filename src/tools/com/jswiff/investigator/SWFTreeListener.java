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

package com.jswiff.investigator;

import java.awt.Frame;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.listeners.SWFListenerAdapter;
import com.jswiff.swfrecords.SWFHeader;
import com.jswiff.swfrecords.actions.ActionBlock;
import com.jswiff.swfrecords.tags.Tag;
import com.jswiff.swfrecords.tags.TagHeader;


/*
 * SWF Listener implementation used to generate a tree while parsing an SWF
 * document.
 */
final class SWFTreeListener extends SWFListenerAdapter {
  private DefaultMutableTreeNode treeNode;
  private Frame parent;
  private ProgressDialog progressDialog;
  private int tagCount;
  private int malformedTagCount;
  private boolean isProtected;

  SWFTreeListener(DefaultMutableTreeNode treeNode, Frame parent) {
    this.treeNode   = treeNode;
    this.parent     = parent;
  }

  /**
   * Contains code executed after parsing (after reading the end tag). The
   * progress dialog is closed, and in case some malformed tags were
   * encountered, a warning dialog is displayed.
   */
  public void postProcess() {
    progressDialog.close();
    if (malformedTagCount != 0) {
      String message = malformedTagCount + " of " + tagCount +
        " parsed tags are malformed!";
      JOptionPane.showMessageDialog(
        parent, message, "Malformed tags", JOptionPane.WARNING_MESSAGE);
      ((Investigator) parent).find("Malformed tag");
    } else {
      ((Investigator) parent).expandRoot();
    }
  }

  /**
   * Contains code executed before parsing (before reading the SWF file
   * header).
   */
  public void preProcess() {
    ActionBlock.resetInstanceCounter();
  }

  /**
   * Contains processing code for the SWF header. Adds the header data to the
   * model.
   *
   * @param header the header of the SWF file
   */
  public void processHeader(SWFHeader header) {
    progressDialog = new ProgressDialog(
        parent, "Parsing Flash file...", "Reading file header...", "", 0,
        (int) header.getFileLength(), false);
    SWFTreeBuilder.setNodes(0);
    SWFTreeBuilder.addNode(treeNode, header);
    progressDialog.setProgressValue(21);
    // 21 bytes are only an average value, exact value not really relevant
    progressDialog.setNote("Done.");
  }

  /**
   * In case of an SWF header read error, this method displays an error message
   * at the console and within a dialog, and prints the stack trace.
   *
   * @param e the exception which occured while parsing the header
   */
  public void processHeaderReadError(Exception e) {
    String message = "Malformed file header - parsing aborted.";
    System.out.println(message);
    JOptionPane.showMessageDialog(
      parent, message, "Read error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
  }

  /**
   * Adds each tag to the tree. If a Protect tag is found, the document is
   * marked as protected (which causes a warning message to be displayed).
   *
   * @param tag the current tag read by the <code>SWFReader</code>
   * @param offset the current stream offset
   */
  public void processTag(Tag tag, long offset) {
    progressDialog.setMessage(tagCount++ + " tags read");
    SWFTreeBuilder.addNode(treeNode, tag);
    progressDialog.setProgressValue((int) offset);
    progressDialog.setNote(tag.toString());
    if (TagType.PROTECT.equals(tag.tagType())) {
      isProtected = true;
    }
  }

  /**
   * Prints an error message and the exception's stack trace to the console in
   * case of a tag header read error. A message dialog displays an error.
   *
   * @param e the exception which occured during tag header parsing
   */
  public void processTagHeaderReadError(Exception e) {
    String message = "Malformed tag header - parsing aborted.";
    System.err.println(message);
    JOptionPane.showMessageDialog(
      parent, message, "Read error", JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
  }

  /**
   * Increments the error counter, prints the stack trace to the console and
   * tells the SWF reader to try to continue reading after error processing.
   *
   * @param tagHeader header of the malformed tag
   * @param tagData the tag data as byte array
   * @param e the exception which occured while parsing the tag
   *
   * @return <code>false</code>, i.e. the reader doesn't stop reading further
   *         tags after error processing
   */
  public boolean processTagReadError(
    TagHeader tagHeader, byte[] tagData, Exception e) {
    malformedTagCount++;
    e.printStackTrace();
    return false;
  }

  int getNodeNumber() {
    return SWFTreeBuilder.getNodes();
  }

  boolean isProtected() {
    return isProtected;
  }
}
